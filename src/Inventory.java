import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Inventory.java
 * Manages all grocery items.
 *  - ArrayList  : keeps every Item in the order it was added.
 *  - HashMap    : finds an Item quickly by its ID (key = ID, value = Item).
 *  - File I/O   : loads from / saves to inventory.txt.
 */
public class Inventory {

    private static final String ID_PREFIX = "G";   // IDs look like G101, G102 ...
    private static final int FIRST_ID_NUMBER = 101; // the first ID must be G101
    public static final int LOW_STOCK_LIMIT = 5;    // "low stock" means quantity below 5

    private ArrayList<Item> items = new ArrayList<Item>();
    private HashMap<String, Item> itemMap = new HashMap<String, Item>();
    private int nextIdNumber = FIRST_ID_NUMBER;     // number used for the next new ID
    private String fileName;

    /** Creates an inventory that uses the given file for saving/loading. */
    public Inventory(String fileName) {
        this.fileName = fileName;
    }

    // =====================================================
    // Feature 1: Add Item
    // =====================================================

    /** Creates a new item with an automatic unique ID and stores it. */
    public Item addItem(String name, int quantity, double price) {
        String newId = ID_PREFIX + nextIdNumber;
        nextIdNumber++; // next item gets the next number

        Item item = new Item(newId, name, quantity, price);
        items.add(item);       // store in the ArrayList
        itemMap.put(newId, item); // store in the HashMap for fast lookup
        return item;
    }

    // =====================================================
    // Feature 2: Sell Item
    // =====================================================

    /** Sells the given quantity of an item and returns the message to display. */
    public String sellItem(String itemId, int sellQuantity) {
        Item item = itemMap.get(itemId); // quick lookup using the HashMap

        if (item == null) {
            return "Item not found. No item with ID: " + itemId;
        }

        // Never allow selling more than the available stock
        if (sellQuantity > item.getQuantity()) {
            return "Insufficient stock. Available: " + item.getQuantity();
        }

        item.reduceQuantity(sellQuantity);

        String message = "Sold " + sellQuantity + " of " + item.getName()
                + ". Stock: " + item.getQuantity();
        if (item.isOutOfStock()) {
            message = message + " (Out of stock)";
        }
        return message;
    }

    // =====================================================
    // Feature 3: Display Items Sorted by Name
    // =====================================================

    /** Prints all items alphabetically by name (the original list is not changed). */
    public void displaySortedByName() {
        if (items.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        // Work on a COPY so the original order in 'items' stays untouched
        ArrayList<Item> sortedList = new ArrayList<Item>(items);
        bubbleSortByName(sortedList);

        System.out.println("Sorted inventory:");
        for (int i = 0; i < sortedList.size(); i++) {
            System.out.println((i + 1) + ". " + sortedList.get(i).toDisplayString());
        }
    }

    /**
     * Manual Bubble Sort (no Collections.sort / Arrays.sort).
     * Repeatedly compares neighbouring items and swaps them if they are in the
     * wrong order, so the "largest" name bubbles to the end in each pass.
     */
    private void bubbleSortByName(ArrayList<Item> list) {
        int n = list.size();
        for (int pass = 0; pass < n - 1; pass++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - pass; j++) {
                String first = list.get(j).getName();
                String second = list.get(j + 1).getName();

                // compareToIgnoreCase > 0 means 'first' comes AFTER 'second' alphabetically
                if (first.compareToIgnoreCase(second) > 0) {
                    Item temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) {
                break; // already sorted, stop early
            }
        }
    }

    // =====================================================
    // Feature 4: Low-Stock Report
    // =====================================================

    /** Prints items whose quantity is below LOW_STOCK_LIMIT (5). */
    public void displayLowStockReport() {
        boolean foundAny = false;
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getQuantity() < LOW_STOCK_LIMIT) {
                System.out.println("Low stock: " + item.getId() + " | " + item.getName()
                        + " | Qty: " + item.getQuantity());
                foundAny = true;
            }
        }
        if (!foundAny) {
            System.out.println("No items are low on stock.");
        }
    }

    // =====================================================
    // File Handling
    // =====================================================

    /**
     * Loads items from the file. A missing or empty file is fine (inventory stays empty).
     * Also makes sure new IDs continue after the highest ID found in the file.
     * Returns the number of items loaded.
     */
    public int loadFromFile() {
        File file = new File(fileName);
        if (!file.exists()) {
            return 0; // first run: no file yet
        }

        int loadedCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue; // skip blank lines
                }

                Item item = Item.fromFileLine(line);
                if (item == null || itemMap.containsKey(item.getId())) {
                    continue; // skip damaged lines and duplicate IDs
                }

                items.add(item);
                itemMap.put(item.getId(), item);
                loadedCount++;
                updateNextIdNumber(item.getId());
            }
        } catch (IOException e) {
            System.out.println("Warning: could not read " + fileName + " (" + e.getMessage() + ")");
        }
        return loadedCount;
    }

    /** Saves every item to the file (one line per item). Returns true if successful. */
    public boolean saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < items.size(); i++) {
                writer.write(items.get(i).toFileLine());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error: could not save " + fileName + " (" + e.getMessage() + ")");
            return false;
        }
    }

    /** Keeps nextIdNumber above every ID already used, so IDs never repeat. */
    private void updateNextIdNumber(String id) {
        if (id.startsWith(ID_PREFIX)) {
            try {
                int number = Integer.parseInt(id.substring(ID_PREFIX.length()));
                if (number >= nextIdNumber) {
                    nextIdNumber = number + 1;
                }
            } catch (NumberFormatException e) {
                // ID is not in the G<number> format: ignore it
            }
        }
    }
}
