package service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * TransactionProcessor executes banking transaction tasks using a fixed thread pool.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 10
 */
public class TransactionProcessor {

    private final ExecutorService executor;

    public TransactionProcessor(int poolSize) {
        this.executor = Executors.newFixedThreadPool(poolSize);
    }

    public TransactionProcessor() {
        this(4); // Default fixed thread pool of 4
    }

    public void submit(Runnable task) {
        if (task != null) {
            executor.execute(task);
        }
    }

    public void stop() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
