import java.util.LinkedHashMap;
import java.util.Map;

public class Warehouse {
    private final Map<String, Integer> stockMap = new LinkedHashMap<>();

    public void addStock(String item, int qty) {
        stockMap.put(item, stockMap.getOrDefault(item, 0) + qty);
    }

    public void issue(String item, int qty) throws OutOfStockException, InvalidQuantityException {
        if (qty <= 0) {
            throw new InvalidQuantityException(qty);
        }
        int available = stockMap.getOrDefault(item, 0);
        if (qty > available) {
            throw new OutOfStockException(item, qty, available);
        }
        stockMap.put(item, available - qty);
        System.out.println("  [ISSUED] " + qty + " units of '" + item
                + "' | Remaining stock: " + stockMap.get(item));
    }
}
