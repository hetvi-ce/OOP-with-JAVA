package model;

import java.io.Serializable;
import java.util.Objects;
import model.annotation.Id;
import model.annotation.Positive;
import model.annotation.MaxLength;
import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import exception.AccountNotFoundException;

/**
 * Abstract Account model for MiniBank implementing Transactable, InterestBearing,
 * Serializable, and robust custom exception handling.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 05 through 11
 */
public abstract class Account implements Transactable, InterestBearing, Serializable, Comparable<Account> {

    private static final long serialVersionUID = 1L;

    private static long accountCounter = 0;

    public static synchronized void synchronizeCounter(long maxObserved) {
        if (maxObserved > accountCounter) {
            accountCounter = maxObserved;
        }
    }

    @Id
    private final String accountNumber;

    @MaxLength(value = 50, message = "Owner name exceeds maximum length of 50 characters")
    private String ownerName;

    @Positive(message = "must be > 0")
    private long balance;

    private boolean active;

    private static synchronized String generateAccountNumber() {
        accountCounter++;
        return String.format("AC%04d", accountCounter);
    }

    public Account(String ownerName, long balance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = (ownerName != null) ? ownerName.trim() : "Unknown";
        this.balance = balance;
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    // Abstract methods for polymorphic rules
    @Override
    public abstract double interestRate();
    public abstract boolean canWithdraw(long amount);

    public long calculateShortfall(long amount) {
        return amount > balance ? amount - balance : 0;
    }

    // Practical 09: depositUnsynchronized to demonstrate race condition in multithreaded environments
    public void depositUnsynchronized(long amount) {
        long current = this.balance;
        try {
            // Subtle sleep/yield to increase probability of thread interleaving
            Thread.sleep(0, 500);
        } catch (InterruptedException ignored) {}
        this.balance = current + amount;
    }

    // Practical 08 & 09: synchronized deposit ensuring thread safety
    @Override
    public synchronized void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be strictly positive (> 0), received: ₹" + amount);
        }
        this.balance += amount;
    }

    // Practical 08 & 09: synchronized withdraw ensuring thread safety
    @Override
    public synchronized boolean withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be strictly positive (> 0), received: ₹" + amount);
        }
        if (!canWithdraw(amount)) {
            long shortfall = calculateShortfall(amount);
            throw new InsufficientFundsException("Withdrawal failed for account " + accountNumber + ": insufficient funds.", shortfall);
        }
        this.balance -= amount;
        return true;
    }

    // Practical 08: transfer uses try-catch-finally and rethrows BankException
    public void transfer(Account targetAccount, long amount) throws BankException {
        if (targetAccount == null) {
            throw new AccountNotFoundException("Transfer failed: destination account cannot be null.");
        }
        if (this.equals(targetAccount)) {
            throw new BankException("Transfer failed: cannot transfer funds to the same account (" + accountNumber + ").");
        }

        System.out.printf("  [Transfer] Initiating transfer of ₹%d from %s to %s...%n",
                amount, this.accountNumber, targetAccount.accountNumber);
        boolean debited = false;
        try {
            this.withdraw(amount);
            debited = true;
            targetAccount.deposit(amount);
            System.out.printf("  [Transfer] Successfully transferred ₹%d from %s to %s.%n",
                    amount, this.accountNumber, targetAccount.accountNumber);
        } catch (BankException e) {
            if (debited) {
                // Rollback withdrawal if destination deposit failed
                try {
                    this.deposit(amount);
                } catch (InvalidAmountException ignored) {}
            }
            throw e;
        } finally {
            System.out.printf("  [Transfer Audit] Finalized transfer operation between %s and %s.%n",
                    this.accountNumber, targetAccount.accountNumber);
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        if (ownerName != null) {
            this.ownerName = ownerName.trim();
        }
    }

    @Override
    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format("Account[number=%s, owner='%s', balance=₹%d, active=%s]",
                accountNumber, ownerName, balance, active);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Account other)) return false;
        return Objects.equals(this.accountNumber, other.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public int compareTo(Account other) {
        if (other == null) return 1;
        if (this.accountNumber == null && other.accountNumber == null) return 0;
        if (this.accountNumber == null) return -1;
        if (other.accountNumber == null) return 1;
        return this.accountNumber.compareTo(other.accountNumber);
    }
}
