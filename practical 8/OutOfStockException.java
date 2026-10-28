public class OutOfStockException extends Exception {
    private final int shortfall;

    public OutOfStockException(String item, int requested, int available) {
        super("Insufficient stock for '" + item + "' (requested=" + requested + ", available=" + available + ")");
        this.shortfall = requested - available;
    }

    public int getShortfall() {
        return shortfall;
    }
}
