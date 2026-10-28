public class StockIssueDemo {
    public static void main(String[] args) {
        Warehouse labStore = new Warehouse();
        labStore.addStock("Arduino_Uno", 12);
        labStore.addStock("OLED_Display", 5);
        labStore.addStock("Servo_Motor", 8);

        String[] items = {"Arduino_Uno", "OLED_Display", "Servo_Motor", "Arduino_Uno", "OLED_Display"};
        int[] quantities = {4, 9, -2, 6, 3};

        System.out.println("Processing 5 warehouse dispatch requests:");
        for (int i = 0; i < items.length; i++) {
            System.out.println("Request #" + (i + 1) + ": item=" + items[i] + ", qty=" + quantities[i]);
            try {
                labStore.issue(items[i], quantities[i]);
            } catch (InvalidQuantityException e) {
                System.out.println("  [REJECTED - INVALID QTY] " + e.getMessage());
            } catch (OutOfStockException e) {
                System.out.println("  [REJECTED - OUT OF STOCK] " + e.getMessage()
                        + " | Shortfall: " + e.getShortfall() + " units");
            }
        }
        System.out.println("All dispatch requests processed without halting execution.");
    }
}
