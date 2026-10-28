import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class LogAnalyzerDemo {
    public static void main(String[] args) throws IOException {
        Path logDir = Path.of("server_logs_105");
        Files.createDirectories(logDir);

        Path authLog = logDir.resolve("auth_service.log");
        Path dbLog = logDir.resolve("db_engine.log");
        Path apiLog = logDir.resolve("gateway_api.log");

        Files.write(authLog, List.of(
                "2026-10-25 09:00 INFO  User 25CE105 logged in",
                "2026-10-25 09:04 ALERT Failed token verification from 10.0.4.12",
                "2026-10-25 09:10 INFO  Session refreshed for Hetvi Sangani"
        ));
        Files.write(dbLog, List.of(
                "2026-10-25 09:12 INFO  Connection pool initialized (size=10)",
                "2026-10-25 09:15 ALERT Slow query detected on table enrollments",
                "2026-10-25 09:18 ALERT Deadlock retry triggered on shard-2",
                "2026-10-25 09:20 INFO  Checkpoint completed"
        ));
        Files.write(apiLog, List.of(
                "2026-10-25 09:22 INFO  GET /api/courses -> 200 OK",
                "2026-10-25 09:25 INFO  POST /api/submit -> 201 Created"
        ));

        Path[] logFiles = {authLog, dbLog, apiLog};
        String keyword = "ALERT";
        int totalLines = 0;
        int keywordMatches = 0;

        System.out.println("Analyzing log files for keyword '" + keyword + "':");
        for (Path file : logFiles) {
            BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
            List<String> lines = Files.readAllLines(file);
            int fileHits = 0;
            for (String line : lines) {
                totalLines++;
                if (line.contains(keyword)) {
                    keywordMatches++;
                    fileHits++;
                }
            }
            System.out.println("  File: " + file.getFileName()
                    + " | Size: " + attrs.size() + " bytes"
                    + " | Lines: " + lines.size()
                    + " | '" + keyword + "' hits: " + fileHits);
        }

        System.out.println("Summary across all files -> Total Lines: " + totalLines
                + " | Lines containing '" + keyword + "': " + keywordMatches);
    }
}
