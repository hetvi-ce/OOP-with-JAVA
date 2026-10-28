public class CounterRaceDemo {
    static class SharedCounter {
        private int unsafeValue = 0;
        private int safeValue = 0;

        public void incrementUnsafe() {
            int temp = unsafeValue;
            Thread.yield();
            unsafeValue = temp + 1;
        }

        public synchronized void incrementSynchronized() {
            safeValue++;
        }

        public int getUnsafeValue() {
            return unsafeValue;
        }

        public int getSafeValue() {
            return safeValue;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int threadCount = 8;
        final int incrementsPerThread = 5000;
        final int expectedTotal = threadCount * incrementsPerThread;

        SharedCounter counter = new SharedCounter();

        Thread[] unsafeThreads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            unsafeThreads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.incrementUnsafe();
                }
            });
            unsafeThreads[i].start();
        }
        for (Thread t : unsafeThreads) {
            t.join();
        }

        System.out.println("Without synchronization -> Expected: " + expectedTotal
                + " | Actual total: " + counter.getUnsafeValue());

        Thread[] safeThreads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            safeThreads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.incrementSynchronized();
                }
            });
            safeThreads[i].start();
        }
        for (Thread t : safeThreads) {
            t.join();
        }

        System.out.println("With synchronized increment -> Expected: " + expectedTotal
                + " | Actual total: " + counter.getSafeValue());
    }
}
