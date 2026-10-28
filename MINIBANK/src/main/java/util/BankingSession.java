package util;

/**
 * AutoCloseable banking session to demonstrate try-with-resources resource management.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 08
 */
public class BankingSession implements AutoCloseable {

    private final String sessionUser;
    private final long startTime;

    public BankingSession(String sessionUser) {
        this.sessionUser = sessionUser;
        this.startTime = System.currentTimeMillis();
        System.out.printf("  [Session] Secure banking session opened for %s.%n", sessionUser);
    }

    public void auditAction(String action) {
        System.out.printf("  [Session] Verified action: %s%n", action);
    }

    @Override
    public void close() {
        long duration = System.currentTimeMillis() - startTime;
        System.out.printf("  [Session] Secure banking session for %s closed automatically (duration: %d ms).%n",
                sessionUser, duration);
    }
}
