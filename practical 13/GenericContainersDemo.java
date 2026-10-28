public class GenericContainersDemo {
    public static <E> E firstElement(E[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return array[0];
    }

    public static void main(String[] args) {
        Box<String> courseBox = new Box<>("CEUE203 - Object-Oriented Programming");
        Box<Integer> creditBox = new Box<>(4);

        String courseTitle = courseBox.get();
        int courseCredits = creditBox.get();
        System.out.println("Box<String> value (no cast)  : " + courseTitle);
        System.out.println("Box<Integer> value (no cast) : " + courseCredits);

        Pair<String, Integer> studentRank = new Pair<>("25CE105 - Hetvi Sangani", 1);
        Pair<String, Double> sensorReading = new Pair<>("Lab-Thermistor", 26.8);
        System.out.println("Pair<String, Integer>        : " + studentRank);
        System.out.println("Pair<String, Double>         : " + sensorReading);

        String[] deptCodes = {"CE", "IT", "EC", "CSE"};
        Double[] gpaList = {9.15, 8.80, 9.42};

        System.out.println("First of String[]            : " + firstElement(deptCodes));
        System.out.println("First of Double[]            : " + firstElement(gpaList));
    }
}
