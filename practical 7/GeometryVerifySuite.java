public class GeometryVerifySuite {
    @Run
    public void verifyCircleArea() {
        double radius = 7.0;
        double area = Math.PI * radius * radius;
        System.out.printf("  [PASS] verifyCircleArea: radius=%.1f -> area=%.2f%n", radius, area);
    }

    @Run
    public void verifyRectanglePerimeter() {
        int width = 8;
        int height = 5;
        int perimeter = 2 * (width + height);
        System.out.println("  [PASS] verifyRectanglePerimeter: 2*(8+5) = " + perimeter);
    }

    public void helperResetState() {
        System.out.println("  [SKIP] helperResetState is not marked with @Run");
    }

    @Run
    public void verifyPythagoreanHypotenuse() {
        double a = 6.0;
        double b = 8.0;
        double c = Math.hypot(a, b);
        System.out.println("  [PASS] verifyPythagoreanHypotenuse: hypot(6, 8) = " + c);
    }
}
