import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class DeadlockOrderDemo {
    static class LabResource {
        private final int id;
        private final String name;

        public LabResource(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    private static volatile boolean releaseDeadlock = false;

    public static void acquireInconsistentOrder(String worker, LabResource first, LabResource second) {
        synchronized (first) {
            System.out.println("  [" + worker + "] Locked " + first.getName()
                    + ", waiting for " + second.getName() + "...");
            try {
                Thread.sleep(40);
            } catch (InterruptedException ignored) {
            }
            while (!releaseDeadlock) {
                synchronized (second) {
                    System.out.println("  [" + worker + "] Acquired both resources.");
                    return;
                }
            }
        }
    }

    public static void acquireConsistentOrder(String worker, LabResource resA, LabResource resB) {
        LabResource first = (resA.getId() < resB.getId()) ? resA : resB;
        LabResource second = (resA.getId() < resB.getId()) ? resB : resA;

        synchronized (first) {
            System.out.println("  [" + worker + "] Locked lower-ID " + first.getName() + " (#" + first.getId() + ")");
            synchronized (second) {
                System.out.println("  [" + worker + "] Locked higher-ID " + second.getName()
                        + " (#" + second.getId() + ") -> Task completed safely.");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LabResource printer = new LabResource(101, "3D-Printer");
        LabResource scanner = new LabResource(202, "Laser-Scanner");

        System.out.println("=== Step 1: Reproducing Deadlock with Opposite Lock Order ===");
        Thread t1 = new Thread(() -> {
            synchronized (printer) {
                System.out.println("  [Worker-1] Locked " + printer.getName() + ", trying to lock " + scanner.getName());
                try {
                    Thread.sleep(60);
                } catch (InterruptedException ignored) {
                }
                if (!releaseDeadlock) {
                    synchronized (scanner) {
                        System.out.println("  [Worker-1] Acquired both.");
                    }
                }
            }
        }, "Worker-1");

        Thread t2 = new Thread(() -> {
            synchronized (scanner) {
                System.out.println("  [Worker-2] Locked " + scanner.getName() + ", trying to lock " + printer.getName());
                try {
                    Thread.sleep(60);
                } catch (InterruptedException ignored) {
                }
                if (!releaseDeadlock) {
                    synchronized (printer) {
                        System.out.println("  [Worker-2] Acquired both.");
                    }
                }
            }
        }, "Worker-2");

        t1.setDaemon(true);
        t2.setDaemon(true);
        t1.start();
        t2.start();
        Thread.sleep(150);

        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] deadlockedIds = bean.findMonitorDeadlockedThreads();
        System.out.println("  [Detector] Deadlock detected between "
                + (deadlockedIds != null ? deadlockedIds.length : 0)
                + " threads (Worker-1 State=" + t1.getState() + ", Worker-2 State=" + t2.getState() + ").");

        System.out.println("\n=== Step 2: Resolving Deadlock with Consistent Lock Ordering ===");
        LabResource cleanPrinter = new LabResource(101, "3D-Printer");
        LabResource cleanScanner = new LabResource(202, "Laser-Scanner");

        Thread safe1 = new Thread(() -> acquireConsistentOrder("SafeWorker-1", cleanPrinter, cleanScanner));
        Thread safe2 = new Thread(() -> acquireConsistentOrder("SafeWorker-2", cleanScanner, cleanPrinter));

        safe1.start();
        safe2.start();
        safe1.join();
        safe2.join();
        System.out.println("Both threads completed cleanly with consistent lock ordering.");
    }
}
