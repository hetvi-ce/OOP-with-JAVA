package model;

/**
 * FixedDepositAccount subclass with locked deposit and 7.0% interest rate.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 05 & 06
 */
public class FixedDepositAccount extends Account {

    private static final long serialVersionUID = 1L;

    private int tenureMonths;

    public FixedDepositAccount(String ownerName, long balance, int tenureMonths) {
        super(ownerName, balance);
        this.tenureMonths = Math.max(1, tenureMonths);
    }

    public FixedDepositAccount(String ownerName, long balance) {
        this(ownerName, balance, 12);
    }

    public int getTenureMonths() {
        return tenureMonths;
    }

    public void setTenureMonths(int tenureMonths) {
        this.tenureMonths = tenureMonths;
    }

    @Override
    public double interestRate() {
        return 7.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return false;
    }

    @Override
    public long calculateShortfall(long amount) {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("FixedDepositAccount[number=%s, owner='%s', balance=₹%d, tenure=%d months, rate=%.1f%%, active=%s]",
                getAccountNumber(), getOwnerName(), getBalance(), tenureMonths, interestRate(), isActive());
    }
}
