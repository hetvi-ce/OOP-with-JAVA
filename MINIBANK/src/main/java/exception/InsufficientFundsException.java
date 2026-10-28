package exception;

/**
 * Exception thrown when a withdrawal or transfer exceeds the account balance/limit.
 * Carries the calculated shortfall amount.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 08
 */
public class InsufficientFundsException extends BankException {

    private final long shortfall;

    public InsufficientFundsException(String message, long shortfall) {
        super(message);
        this.shortfall = shortfall;
    }

    public InsufficientFundsException(long shortfall) {
        super("Insufficient funds in account. Shortfall: ₹" + shortfall);
        this.shortfall = shortfall;
    }

    public long getShortfall() {
        return shortfall;
    }
}
