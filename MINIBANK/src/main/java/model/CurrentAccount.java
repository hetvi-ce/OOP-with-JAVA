package model;

/**
 * CurrentAccount subclass with overdraft facility and Premium marker interface.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 05 & 06
 */
public class CurrentAccount extends Account implements Premium {

    private static final long serialVersionUID = 1L;

    private long overdraftLimit;

    public CurrentAccount(String ownerName, long balance, long overdraftLimit) {
        super(ownerName, balance);
        this.overdraftLimit = Math.max(0, overdraftLimit);
    }

    public CurrentAccount(String ownerName, long balance) {
        this(ownerName, balance, 10000);
    }

    public CurrentAccount(String ownerName) {
        this(ownerName, 0, 10000);
    }

    public long getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(long overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public double interestRate() {
        return 0.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return amount > 0 && (getBalance() - amount >= -overdraftLimit);
    }

    @Override
    public long calculateShortfall(long amount) {
        long totalCapacity = getBalance() + overdraftLimit;
        return amount > totalCapacity ? amount - totalCapacity : 0;
    }

    @Override
    public String toString() {
        return String.format("CurrentAccount[number=%s, owner='%s', balance=₹%d, overdraftLimit=₹%d, rate=%.1f%%, active=%s, tier=PREMIUM]",
                getAccountNumber(), getOwnerName(), getBalance(), overdraftLimit, interestRate(), isActive());
    }
}
