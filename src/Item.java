/**
 * Item.java
 * Represents ONE grocery item stored in the inventory.
 * Each item has an ID, a name, a quantity in stock and a price.
 */
public class Item {

    // Private fields (encapsulation): they can only be changed through methods
    private String id;
    private String name;
    private int quantity;
    private double price;

    /** Constructor: creates a new item with all its details. */
    public Item(String id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // ---------- Getters ----------
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    // ---------- Behaviour ----------

    /** Reduces the stock by the given amount (the caller checks stock first). */
    public void reduceQuantity(int amount) {
        quantity = quantity - amount;
    }

    /** Returns true when no stock is left. */
    public boolean isOutOfStock() {
        return quantity == 0;
    }

    /** Shows the price without ".0" when it is a whole number (60.0 -> "60"). */
    public String getFormattedPrice() {
        if (price == (long) price) {
            return String.valueOf((long) price);
        }
        return String.valueOf(price);
    }

    /** Text used when showing the item on screen: G101 | Rice | Qty: 20 | Price: 60 */
    public String toDisplayString() {
        return id + " | " + name + " | Qty: " + quantity + " | Price: " + getFormattedPrice();
    }

    /** One line of text used to save the item in inventory.txt: G101|Rice|20|60 */
    public String toFileLine() {
        return id + "|" + name + "|" + quantity + "|" + getFormattedPrice();
    }

    /**
     * Builds an Item from one line of inventory.txt.
     * Returns null if the line is damaged, so the program can simply skip it.
     */
    public static Item fromFileLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 4) {
            return null; // wrong number of fields
        }
        try {
            String id = parts[0].trim();
            String name = parts[1].trim();
            int quantity = Integer.parseInt(parts[2].trim());
            double price = Double.parseDouble(parts[3].trim());

            if (id.isEmpty() || name.isEmpty() || quantity < 0 || price < 0) {
                return null; // invalid values
            }
            return new Item(id, name, quantity, price);
        } catch (NumberFormatException e) {
            return null; // quantity or price is not a number
        }
    }
}
