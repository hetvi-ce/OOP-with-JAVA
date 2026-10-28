public class InvalidQuantityException extends Exception {
    public InvalidQuantityException(int quantity) {
        super("Invalid request quantity: " + quantity + " (quantity must be > 0)");
    }
}
