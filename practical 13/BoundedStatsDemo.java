public class BoundedStatsDemo {
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for (N val : values) {
            sum += val.doubleValue();
        }
        return sum / values.length;
    }

    public static void main(String[] args) {
        Integer[] quizScores = {18, 20, 17, 19, 16};
        Double[] labWeights = {12.5, 15.0, 14.25, 16.25};
        Long[] packetCounts = {1200L, 1850L, 1450L, 1500L};

        System.out.printf("Average of Integer[] : %.2f%n", average(quizScores));
        System.out.printf("Average of Double[]  : %.2f%n", average(labWeights));
        System.out.printf("Average of Long[]    : %.2f%n", average(packetCounts));

        // Note: Passing String[] (e.g., average(new String[]{"A", "B"})) is rejected at compile time
        // because String does not extend java.lang.Number, guaranteeing compile-time type safety.
        System.out.println("Note: Non-Number arrays (like String[]) are rejected at compile time by <N extends Number>.");
    }
}
