import java.util.Scanner;

public class GuardedCalculator {
    public static double compute(double left, double right, String op) throws DivideByZeroException {
        return switch (op) {
            case "+" -> left + right;
            case "-" -> left - right;
            case "*" -> left * right;
            case "/" -> {
                if (right == 0.0) {
                    throw new DivideByZeroException("Division by zero is undefined (" + left + " / 0).");
                }
                yield left / right;
            }
            default -> throw new IllegalArgumentException("Unsupported operator: '" + op + "'");
        };
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int attemptNumber = 0;
        boolean completed = false;

        while (!completed && scanner.hasNextLine()) {
            attemptNumber++;
            System.out.print("Enter expression (num1 operator num2): ");
            String line = scanner.nextLine().trim();
            System.out.println(line);

            String[] tokens = line.split("\\s+");
            if (tokens.length < 3) {
                System.out.println("Error: Please enter two numbers and an operator separated by spaces.");
                continue;
            }

            try {
                double firstNum = Double.parseDouble(tokens[0]);
                String operator = tokens[1];
                double secondNum = Double.parseDouble(tokens[2]);

                double answer = compute(firstNum, secondNum, operator);
                System.out.println("Calculation succeeded: " + firstNum + " " + operator + " " + secondNum + " = " + answer);
                completed = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Number Error: Could not parse numeric operand (" + e.getMessage() + ").");
            } catch (DivideByZeroException e) {
                System.out.println("Math Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Operator Error: " + e.getMessage());
            } finally {
                System.out.println("[Finally Log] Attempt #" + attemptNumber + " processed.");
            }
        }
    }
}
