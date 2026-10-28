package util;

/**
 * Command record representing a parsed banking transaction operation.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 04 & 06
 */
public record Command(TransactionType type, String accountNumber, long amount) {
    @Override
    public String toString() {
        return String.format("Command[type=%s, account=%s, amount=₹%d]", type, accountNumber, amount);
    }
}
