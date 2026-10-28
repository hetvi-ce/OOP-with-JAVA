package model;

/**
 * SavingsAccount subclass with minimum balance constraint and 4.0% interest rate.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 05 & 06
 */
public class SavingsAccount extends Account {

    private static final long serialVersionUID = 1L;

    private long minBalance;

    public SavingsAccount(String ownerName, long balance, long minBalance) {
        super(ownerName, balance);
        this.minBalance = Math.max(0, minBalance);
    }

    public SavingsAccount(String ownerName, long balance) {
        this(ownerName, balance, 1000);
    }

    public SavingsAccount(String ownerName) {
        this(ownerName, 1000, 1000);
    }

    public long getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(long minBalance) {
        this.minBalance = minBalance;
    }

    @Override
    public double interestRate() {
        return 4.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return amount > 0 && (getBalance() - amount >= minBalance);
    }

    @Override
    public long calculateShortfall(long amount) {
        long effectiveAvailable = Math.max(0, getBalance() - minBalance);
        return amount > effectiveAvailable ? amount - effectiveAvailable : 0;
    }

    @Override
    public String toString() {
        return String.format("SavingsAccount[number=%s, owner='%s', balance=₹%d, minBalance=₹%d, rate=%.1f%%, active=%s]",
                getAccountNumber(), getOwnerName(), getBalance(), minBalance, interestRate(), isActive());
    }
}
