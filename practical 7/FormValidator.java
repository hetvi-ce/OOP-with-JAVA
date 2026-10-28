import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FormValidator {
    public static List<String> validate(Object formObj) {
        List<String> violations = new ArrayList<>();
        Field[] declaredFields = formObj.getClass().getDeclaredFields();

        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                Object rawValue = field.get(formObj);
                String text = (rawValue == null) ? "" : rawValue.toString();

                if (field.isAnnotationPresent(NotBlank.class)) {
                    NotBlank rule = field.getAnnotation(NotBlank.class);
                    if (rawValue == null || text.trim().isEmpty()) {
                        violations.add(field.getName() + ": " + rule.message());
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class) && rawValue != null) {
                    MaxLength maxRule = field.getAnnotation(MaxLength.class);
                    if (text.length() > maxRule.value()) {
                        violations.add(field.getName() + ": length " + text.length()
                                + " exceeds max limit of " + maxRule.value());
                    }
                }
            } catch (IllegalAccessException e) {
                violations.add(field.getName() + ": reflection access error");
            }
        }
        return violations;
    }

    public static void main(String[] args) {
        SignupForm validApplicant = new SignupForm("hetvi_105", "hetvi@charusat.edu.in", "CHARU26");
        SignupForm invalidApplicant = new SignupForm("   ", "hetvi.sangani@charusat.edu.in", "CAMPUS_AMBASSADOR_2026");

        System.out.println("Checking Valid Form -> " + validApplicant);
        List<String> validErrors = validate(validApplicant);
        System.out.println("Validation Errors (" + validErrors.size() + "): " + validErrors);

        System.out.println("\nChecking Invalid Form -> " + invalidApplicant);
        List<String> invalidErrors = validate(invalidApplicant);
        System.out.println("Validation Errors (" + invalidErrors.size() + "):");
        for (String err : invalidErrors) {
            System.out.println(" - " + err);
        }
    }
}
