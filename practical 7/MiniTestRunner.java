import java.lang.reflect.Method;

public class MiniTestRunner {
    public static void main(String[] args) throws Exception {
        GeometryVerifySuite suiteInstance = new GeometryVerifySuite();
        Class<?> suiteClass = suiteInstance.getClass();

        System.out.println("Scanning test class: " + suiteClass.getSimpleName());
        int executedCount = 0;
        int skippedCount = 0;

        for (Method method : suiteClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Run.class) && method.getParameterCount() == 0) {
                System.out.println("Invoking @Run method: " + method.getName() + "()");
                method.invoke(suiteInstance);
                executedCount++;
            } else {
                skippedCount++;
            }
        }

        System.out.println("Summary -> Total @Run methods executed: " + executedCount
                + " | Non-@Run methods skipped: " + skippedCount);
    }
}
