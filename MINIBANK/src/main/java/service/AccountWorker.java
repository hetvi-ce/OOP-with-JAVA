package service;

import model.Account;
import exception.InvalidAmountException;

/**
 * AccountWorker executes repetitive concurrent deposit tasks across worker threads.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 09
 */
public class AccountWorker implements Runnable {

    private final Account account;
    private final int times;
    private final long amount;
    private final boolean useSynchronized;

    public AccountWorker(Account account, int times, long amount, boolean useSynchronized) {
        this.account = account;
        this.times = times;
        this.amount = amount;
        this.useSynchronized = useSynchronized;
    }

    public AccountWorker(Account account, int times, long amount) {
        this(account, times, amount, true);
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        // Print thread name to observe lifecycle
        for (int i = 0; i < times; i++) {
            if (useSynchronized) {
                try {
                    account.deposit(amount);
                } catch (InvalidAmountException e) {
                    System.err.printf("[%s] Deposit error: %s%n", threadName, e.getMessage());
                }
            } else {
                account.depositUnsynchronized(amount);
            }
        }
    }
}
