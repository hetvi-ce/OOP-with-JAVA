import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardDemo {
    public static void main(String[] args) {
        Map<String, Integer> playerScores = new LinkedHashMap<>();
        playerScores.put("Hetvi Sangani", 940);
        playerScores.put("Aarav Joshi", 880);
        playerScores.put("Tanvi Desai", 940);
        playerScores.put("Devansh Shah", 790);
        playerScores.put("Krisha Amin", 910);
        playerScores.put("Mihir Trivedi", 880);

        List<Map.Entry<String, Integer>> ranking = new ArrayList<>(playerScores.entrySet());
        Comparator<Map.Entry<String, Integer>> scoreDescThenNameAsc = Comparator
                .<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue)
                .reversed()
                .thenComparing(Map.Entry::getKey);

        ranking.sort(scoreDescThenNameAsc);

        int topN = 4;
        System.out.println("Coding Contest Leaderboard (Top " + topN + " Players, ties broken by name):");
        for (int i = 0; i < Math.min(topN, ranking.size()); i++) {
            Map.Entry<String, Integer> entry = ranking.get(i);
            System.out.printf("  Rank #%d : %-16s | Score: %d%n",
                    (i + 1), entry.getKey(), entry.getValue());
        }
    }
}
