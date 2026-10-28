import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRunnerDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        System.out.println("Submitting 10 image-rendering tasks to FixedThreadPool(3):");

        for (int taskId = 1; taskId <= 10; taskId++) {
            final int currentTask = taskId;
            pool.submit(() -> {
                String workerName = Thread.currentThread().getName();
                System.out.println("  Task #" + currentTask + " executed by " + workerName);
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        pool.shutdown();
        boolean finished = pool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Pool shutdown complete (all 10 tasks finished=" + finished
                + ", 3 worker threads reused).");
    }
}
