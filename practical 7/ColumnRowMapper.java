import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ColumnRowMapper {
    public static <T> T populate(Class<T> targetClass, String[] headers, String[] rowValues) throws Exception {
        Map<String, String> columnMap = new HashMap<>();
        int count = Math.min(headers.length, rowValues.length);
        for (int i = 0; i < count; i++) {
            columnMap.put(headers[i].trim(), rowValues[i].trim());
        }

        T record = targetClass.getDeclaredConstructor().newInstance();
        for (Field field : targetClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                Column col = field.getAnnotation(Column.class);
                String colName = col.name();
                field.setAccessible(true);

                if (!columnMap.containsKey(colName)) {
                    System.out.println("  [Notice] Missing column '" + colName
                            + "' for field '" + field.getName() + "' -> keeping default value.");
                    continue;
                }

                String raw = columnMap.get(colName);
                if (field.getType() == int.class || field.getType() == Integer.class) {
                    field.set(record, Integer.parseInt(raw));
                } else if (field.getType() == double.class || field.getType() == Double.class) {
                    field.set(record, Double.parseDouble(raw));
                } else {
                    field.set(record, raw);
                }
            }
        }
        return record;
    }

    public static void main(String[] args) throws Exception {
        String[] fullHeaders = {"STUDENT_ID", "STUDENT_NAME", "CREDITS", "LAB_BATCH"};
        String[] rowOne = {"25CE105", "Hetvi Sangani", "4", "CE-B2"};

        System.out.println("Mapping Row 1 (all columns present):");
        CourseEnrollment first = populate(CourseEnrollment.class, fullHeaders, rowOne);
        System.out.println("  Result -> " + first);

        String[] partialHeaders = {"STUDENT_ID", "STUDENT_NAME", "CREDITS"};
        String[] rowTwo = {"25CE108", "Kavya Joshi", "3"};

        System.out.println("\nMapping Row 2 (missing 'LAB_BATCH' column):");
        CourseEnrollment second = populate(CourseEnrollment.class, partialHeaders, rowTwo);
        System.out.println("  Result -> " + second);
    }
}
