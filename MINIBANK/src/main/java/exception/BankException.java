package exception;

/**
 * Checked base exception for all banking-related failures in MiniBank.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 08
 */
public class BankException extends Exception {

    public BankException(String message) {
        super(message);
    }

    public BankException(String message, Throwable cause) {
        super(message, cause);
    }
}
