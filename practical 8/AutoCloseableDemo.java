public class AutoCloseableDemo {
    public static void main(String[] args) {
        System.out.println("=== Case 1: Normal Execution inside try-with-resources ===");
        try (SpectrometerSession session1 = new SpectrometerSession("SPEC-105-A")) {
            session1.scanSample("Chlorophyll-A", 430);
        } catch (Exception e) {
            System.out.println("Caught Exception: " + e.getMessage());
        }

        System.out.println("\n=== Case 2: Exception Thrown inside try-with-resources ===");
        try (SpectrometerSession session2 = new SpectrometerSession("SPEC-105-B")) {
            session2.scanSample("Benzene-Extract", 260);
            session2.scanSample("Invalid-UV-Sample", 950);
        } catch (IllegalArgumentException e) {
            System.out.println("[Primary Error Reported] " + e.getClass().getSimpleName() + ": " + e.getMessage());
            System.out.println("Suppressed exceptions count: " + e.getSuppressed().length);
        }
    }
}
