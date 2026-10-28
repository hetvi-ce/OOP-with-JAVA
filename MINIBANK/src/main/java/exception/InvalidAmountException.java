package exception;

/**
 * Exception thrown when a monetary transaction amount is non-positive or invalid.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 08
 */
public class InvalidAmountException extends BankException {

    public InvalidAmountException(String message) {
        super(message);
    }
}
