import java.util.concurrent.atomic.AtomicLong;

public class ParallelArraySumDemo {
    private static long unsafeTotal = 0;
    private static long syncTotal = 0;
    private static final Object LOCK = new Object();
    private static final AtomicLong atomicTotal = new AtomicLong(0);

    public static void main(String[] args) throws InterruptedException {
        final int size = 40_000;
        final int threadsCount = 4;
        int[] readings = new int[size];
        long expectedSum = 0;
        for (int i = 0; i < size; i++) {
            readings[i] = (i % 10) + 1;
            expectedSum += readings[i];
        }

        int chunk = size / threadsCount;

        // 1. Unsynchronized shared total (with occasional yield to expose race)
        Thread[] tUnsafe = new Thread[threadsCount];
        long startUnsafe = System.nanoTime();
        for (int t = 0; t < threadsCount; t++) {
            final int from = t * chunk;
            final int to = (t == threadsCount - 1) ? size : from + chunk;
            tUnsafe[t] = new Thread(() -> {
                for (int i = from; i < to; i++) {
                    long cur = unsafeTotal;
                    if ((i & 63) == 0) {
                        Thread.yield();
                    }
                    unsafeTotal = cur + readings[i];
                }
            });
            tUnsafe[t].start();
        }
        for (Thread t : tUnsafe) {
            t.join();
        }
        double msUnsafe = (System.nanoTime() - startUnsafe) / 1_000_000.0;

        // 2. Fix 1: Synchronized lock accumulation
        Thread[] tSync = new Thread[threadsCount];
        long startSync = System.nanoTime();
        for (int t = 0; t < threadsCount; t++) {
            final int from = t * chunk;
            final int to = (t == threadsCount - 1) ? size : from + chunk;
            tSync[t] = new Thread(() -> {
                for (int i = from; i < to; i++) {
                    synchronized (LOCK) {
                        syncTotal += readings[i];
                    }
                }
            });
            tSync[t].start();
        }
        for (Thread t : tSync) {
            t.join();
        }
        double msSync = (System.nanoTime() - startSync) / 1_000_000.0;

        // 3. Fix 2: Local partial sum per thread + AtomicLong.addAndGet
        Thread[] tAtomic = new Thread[threadsCount];
        long startAtomic = System.nanoTime();
        for (int t = 0; t < threadsCount; t++) {
            final int from = t * chunk;
            final int to = (t == threadsCount - 1) ? size : from + chunk;
            tAtomic[t] = new Thread(() -> {
                long localSubtotal = 0;
                for (int i = from; i < to; i++) {
                    localSubtotal += readings[i];
                }
                atomicTotal.addAndGet(localSubtotal);
            });
            tAtomic[t].start();
        }
        for (Thread t : tAtomic) {
            t.join();
        }
        double msAtomic = (System.nanoTime() - startAtomic) / 1_000_000.0;

        System.out.println("Array Size: " + size + " elements across " + threadsCount + " threads");
        System.out.println("Expected Exact Sum         : " + expectedSum);
        System.out.printf("1) Unsynchronized Sum      : %d (Time: %.2f ms) -> WRONG due to race%n",
                unsafeTotal, msUnsafe);
        System.out.printf("2) Fix A (synchronized)    : %d (Time: %.2f ms) -> EXACT%n",
                syncTotal, msSync);
        System.out.printf("3) Fix B (Local+AtomicLong): %d (Time: %.2f ms) -> EXACT & fastest%n",
                atomicTotal.get(), msAtomic);
    }
}
