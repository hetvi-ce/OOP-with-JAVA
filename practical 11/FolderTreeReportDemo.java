import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FolderTreeReportDemo {
    public static void main(String[] args) throws IOException {
        Path rootDir = Path.of("project_workspace_105");
        Path docsSub = rootDir.resolve("docs");
        Path srcSub = rootDir.resolve("src");
        Files.createDirectories(docsSub);
        Files.createDirectories(srcSub);

        Files.writeString(rootDir.resolve("README.txt"), "Project Workspace for Hetvi Sangani (25CE105)\n");
        Files.writeString(docsSub.resolve("design_notes.txt"), "Architecture notes: NIO FileVisitor walk.\n");
        Files.writeString(srcSub.resolve("AppMain.java"), "public class AppMain { public static void main(String[] a){} }\n");

        List<String> reportLines = new ArrayList<>();
        reportLines.add("NIO FOLDER TREE AUDIT REPORT (Student: Hetvi Sangani - 25CE105)");
        reportLines.add("----------------------------------------------------------------------");
        final long[] counters = {0, 0}; // [0]=fileCount, [1]=totalBytes

        Files.walkFileTree(rootDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                counters[0]++;
                counters[1] += attrs.size();
                String entry = String.format("%-32s | %6d bytes | Modified: %s",
                        rootDir.relativize(file).toString(),
                        attrs.size(),
                        attrs.lastModifiedTime().toString().substring(0, 19));
                reportLines.add(entry);
                return FileVisitResult.CONTINUE;
            }
        });

        reportLines.add("----------------------------------------------------------------------");
        reportLines.add("Total Files Discovered: " + counters[0] + " | Combined Size: " + counters[1] + " bytes");

        Path summaryFile = Path.of("folder_summary_105.txt");
        Files.write(summaryFile, reportLines);

        for (String line : Files.readAllLines(summaryFile)) {
            System.out.println(line);
        }
    }
}
