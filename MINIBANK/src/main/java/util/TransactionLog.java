package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * TransactionLog appends transaction records using NIO.2 Files.write.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 11
 */
public final class TransactionLog {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private TransactionLog() {
        // Utility class
    }

    public static void append(Path file, String line) throws IOException {
        if (file.getParent() != null) {
            Files.createDirectories(file.getParent());
        }
        String timestamped = String.format("[%s] %s%n", LocalDateTime.now().format(FORMATTER), line);
        Files.write(file,
                timestamped.getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }
}
