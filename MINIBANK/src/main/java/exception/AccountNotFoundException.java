package exception;

/**
 * Exception thrown when a requested bank account cannot be found.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 08
 */
public class AccountNotFoundException extends BankException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
