package util;

/**
 * CommandParser parses typed commands (e.g. "DEPOSIT AC0001 500") into Command objects.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 04 & 06
 */
public class CommandParser {

    public static Command parse(String line) {
        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Cannot parse empty or null command line.");
        }

        String[] tokens = line.trim().split("\\s+");
        // Practical 04: Reject command lines that do not have the exact 3 required parts
        if (tokens.length != 3) {
            throw new IllegalArgumentException("Invalid command format. Expected 3 tokens: <TYPE> <ACCOUNT_NUMBER> <AMOUNT>");
        }

        TransactionType type;
        try {
            type = TransactionType.valueOf(tokens[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown transaction type: " + tokens[0] + ". Supported: DEPOSIT, WITHDRAW, TRANSFER");
        }

        String accountNumber = tokens[1].toUpperCase();

        long amount;
        try {
            amount = Long.parseLong(tokens[2]);
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero, got: " + amount);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format: " + tokens[2]);
        }

        return new Command(type, accountNumber, amount);
    }
}
