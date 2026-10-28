import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordFrequencyDemo {
    public static void main(String[] args) {
        String paragraph = "java collections store data efficiently and java generics make java collections type safe and clean";
        String[] words = paragraph.toLowerCase().split("\\s+");

        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String word : words) {
            frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
        }

        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(frequencyMap.entrySet());
        Comparator<Map.Entry<String, Integer>> byFreqDescThenWord = (e1, e2) -> {
            int cmp = Integer.compare(e2.getValue(), e1.getValue());
            return (cmp != 0) ? cmp : e1.getKey().compareTo(e2.getKey());
        };
        sortedEntries.sort(byFreqDescThenWord);

        System.out.println("Input Paragraph: \"" + paragraph + "\"");
        System.out.println("Words sorted by frequency (highest first):");
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            System.out.printf("  %-14s -> %d%n", entry.getKey(), entry.getValue());
        }
    }
}
