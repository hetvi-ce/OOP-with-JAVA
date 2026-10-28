package util;

import model.Account;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * StatePersister handles binary serialization and deserialization of account arrays.
 * Synchronizes Account ID counters to prevent collisions after loading state.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 11
 */
public final class StatePersister {

    private StatePersister() {
        // Utility class
    }

    public static void save(Account[] accounts, Path file) throws IOException {
        if (file.getParent() != null) {
            Files.createDirectories(file.getParent());
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(file))) {
            outputStream.writeObject(accounts);
        }
    }

    public static Account[] load(Path file) throws IOException, ClassNotFoundException {
        if (!Files.exists(file)) {
            return new Account[0];
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(file))) {
            Account[] accounts = (Account[]) inputStream.readObject();
            Account.synchronizeCounter(findMaxAccountId(accounts));
            return accounts;
        }
    }

    // Helper method to find the highest numeric account ID (e.g., "AC0005" -> 5)
    private static long findMaxAccountId(Account[] accounts) {
        long maxId = 0;
        if (accounts == null) {
            return maxId;
        }
        for (Account account : accounts) {
            if (account != null && account.getAccountNumber() != null && account.getAccountNumber().startsWith("AC")) {
                try {
                    long idNumber = Long.parseLong(account.getAccountNumber().substring(2));
                    if (idNumber > maxId) {
                        maxId = idNumber;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }
        return maxId;
    }
}
