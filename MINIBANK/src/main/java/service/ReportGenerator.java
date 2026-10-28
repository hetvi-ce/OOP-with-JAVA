package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ReportGenerator walks transaction logs, aggregates deposits and withdrawals using NIO.2,
 * reads file attributes, and produces structured audit reports.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 11
 */
public final class ReportGenerator {

    private static final Pattern TRANSACTION_PATTERN =
            Pattern.compile(".*(DEPOSIT|WITHDRAW|TRANSFER)\\s+(?:AC\\d+\\s+)?₹?(\\d+).*", Pattern.CASE_INSENSITIVE);

    private ReportGenerator() {
        // Utility class
    }

    public static String generateSummary(Path logsDir, Path outputFile) throws IOException {
        if (!Files.exists(logsDir) || !Files.isDirectory(logsDir)) {
            return "No log directory found at: " + logsDir;
        }

        long totalDeposits = 0;
        long totalWithdrawals = 0;
        int transactionCount = 0;
        int filesProcessed = 0;

        StringBuilder sb = new StringBuilder();
        sb.append("=====================================================\n");
        sb.append("               MINIBANK AUDIT REPORT                 \n");
        sb.append("=====================================================\n\n");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(logsDir, "*.log")) {
            for (Path logFile : stream) {
                filesProcessed++;
                BasicFileAttributes attrs = Files.readAttributes(logFile, BasicFileAttributes.class);
                sb.append(String.format("File: %s | Size: %d bytes | Modified: %s\n",
                        logFile.getFileName(), attrs.size(), attrs.lastModifiedTime()));

                try (BufferedReader reader = Files.newBufferedReader(logFile)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Matcher matcher = TRANSACTION_PATTERN.matcher(line);
                        if (matcher.matches()) {
                            transactionCount++;
                            String type = matcher.group(1).toUpperCase();
                            long amount = Long.parseLong(matcher.group(2));
                            if ("DEPOSIT".equals(type)) {
                                totalDeposits += amount;
                            } else {
                                totalWithdrawals += amount;
                            }
                        }
                    }
                }
            }
        }

        sb.append("\n-----------------------------------------------------\n");
        sb.append(String.format("Log Files Scanned   : %d\n", filesProcessed));
        sb.append(String.format("Total Transactions  : %d\n", transactionCount));
        sb.append(String.format("Total Deposited     : ₹%,d\n", totalDeposits));
        sb.append(String.format("Total Withdrawn     : ₹%,d\n", totalWithdrawals));
        sb.append(String.format("Net Flow            : ₹%,d\n", totalDeposits - totalWithdrawals));
        sb.append("=====================================================\n");

        String report = sb.toString();

        if (outputFile != null) {
            if (outputFile.getParent() != null) {
                Files.createDirectories(outputFile.getParent());
            }
            try (BufferedWriter writer = Files.newBufferedWriter(outputFile)) {
                writer.write(report);
            }
        }

        return report;
    }
}
