import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GroceryInventorySystem {
    private static final String FILE_NAME = "inventory.txt";
    private static final int LOW_STOCK_LIMIT = 5;

    private final ArrayList<GroceryItem> items = new ArrayList<>();
    private final HashMap<String, GroceryItem> itemsById = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);
    private int nextId = 101;

    public static void main(String[] args) {
        GroceryInventorySystem system = new GroceryInventorySystem();
        system.loadInventory();
        system.runMenu();
    }

    private void runMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Grocery Inventory System ===");
            System.out.println("1. Add Item");
            System.out.println("2. Sell Item");
            System.out.println("3. Display Items (Sorted by Name)");
            System.out.println("4. Low-Stock Report");
            System.out.println("5. Exit");
            String choice = readLine("Enter your choice: ");

            if (choice == null) {
                running = false;
            } else {
                switch (choice.trim()) {
                    case "1":
                        addItem();
                        break;
                    case "2":
                        sellItem();
                        break;
                    case "3":
                        displayItems();
                        break;
                    case "4":
                        displayLowStockItems();
                        break;
                    case "5":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number from 1 to 5.");
                }
            }
        }

        saveInventory();
        System.out.println("Inventory saved. Goodbye!");
    }

    private void addItem() {
        String name = readLine("Enter item name: ");
        if (name == null) {
            return;
        }
        name = name.trim();
        while (name.isEmpty() || name.contains("|")) {
            System.out.println("Item name cannot be empty or contain '|'.");
            name = readLine("Enter item name: ");
            if (name == null) {
                return;
            }
            name = name.trim();
        }

        Integer quantity = readInteger("Enter quantity: ", 1);
        if (quantity == null) {
            return;
        }

        Double price = readPrice("Enter price: ");
        if (price == null) {
            return;
        }

        String id = generateId();
        GroceryItem item = new GroceryItem(id, name, quantity, price);
        items.add(item);
        itemsById.put(id, item);
        System.out.println("Item added successfully. Item ID: " + id);
    }

    private void sellItem() {
        String id = readLine("Enter item ID: ");
        if (id == null) {
            return;
        }

        GroceryItem item = itemsById.get(id.trim());
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }
        if (item.getQuantity() == 0) {
            System.out.println("Out of Stock");
            return;
        }

        Integer quantityToSell = readInteger("Enter selling quantity: ", 1);
        if (quantityToSell == null) {
            return;
        }
        if (quantityToSell > item.getQuantity()) {
            System.out.println("Cannot sell more than the available quantity ("
                    + item.getQuantity() + ").");
            return;
        }

        item.setQuantity(item.getQuantity() - quantityToSell);
        System.out.println("Sale completed. Remaining quantity: " + item.getQuantity());
        if (item.getQuantity() == 0) {
            System.out.println("Out of Stock");
        }
    }

    private void displayItems() {
        if (items.isEmpty()) {
            System.out.println("No items in inventory.");
            return;
        }

        ArrayList<GroceryItem> sortedItems = new ArrayList<>(items);
        for (int i = 0; i < sortedItems.size() - 1; i++) {
            for (int j = 0; j < sortedItems.size() - 1 - i; j++) {
                if (sortedItems.get(j).getName()
                        .compareToIgnoreCase(sortedItems.get(j + 1).getName()) > 0) {
                    GroceryItem temporary = sortedItems.get(j);
                    sortedItems.set(j, sortedItems.get(j + 1));
                    sortedItems.set(j + 1, temporary);
                }
            }
        }

        System.out.printf("%-8s %-20s %-10s %-10s%n", "ID", "Name", "Quantity", "Price");
        for (GroceryItem item : sortedItems) {
            System.out.printf("%-8s %-20s %-10d $%-9.2f%n",
                    item.getId(), item.getName(), item.getQuantity(), item.getPrice());
        }
    }

    private void displayLowStockItems() {
        boolean foundItem = false;
        System.out.println("Low-stock items (quantity below " + LOW_STOCK_LIMIT + "):");
        for (GroceryItem item : items) {
            if (item.getQuantity() < LOW_STOCK_LIMIT) {
                System.out.printf("%s - %s: %d%n",
                        item.getId(), item.getName(), item.getQuantity());
                foundItem = true;
            }
        }
        if (!foundItem) {
            System.out.println("No low-stock items.");
        }
    }

    private String generateId() {
        while (itemsById.containsKey("G" + nextId)) {
            nextId++;
        }
        return "G" + nextId++;
    }

    private Integer readInteger(String prompt, int minimum) {
        while (true) {
            String input = readLine(prompt);
            if (input == null) {
                return null;
            }
            try {
                int value = Integer.parseInt(input.trim());
                if (value >= minimum) {
                    return value;
                }
            } catch (NumberFormatException exception) {
                // Continue prompting until a valid integer is entered.
            }
            System.out.println("Please enter a whole number greater than or equal to " + minimum + ".");
        }
    }

    private Double readPrice(String prompt) {
        while (true) {
            String input = readLine(prompt);
            if (input == null) {
                return null;
            }
            try {
                double price = Double.parseDouble(input.trim());
                if (Double.isFinite(price) && price >= 0) {
                    return price;
                }
            } catch (NumberFormatException exception) {
                // Continue prompting until a valid price is entered.
            }
            System.out.println("Please enter a valid non-negative price.");
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }

    private void loadInventory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|", -1);
                if (fields.length != 4) {
                    System.out.println("Skipping invalid inventory record: " + line);
                    continue;
                }

                try {
                    String id = fields[0].trim();
                    String name = fields[1].trim();
                    int quantity = Integer.parseInt(fields[2].trim());
                    double price = Double.parseDouble(fields[3].trim());
                    if (!id.matches("G\\d+") || name.isEmpty() || name.contains("|")
                            || quantity < 0 || !Double.isFinite(price) || price < 0
                            || itemsById.containsKey(id)) {
                        throw new IllegalArgumentException();
                    }

                    GroceryItem item = new GroceryItem(id, name, quantity, price);
                    items.add(item);
                    itemsById.put(id, item);
                    int numericId = Integer.parseInt(id.substring(1));
                    if (numericId >= nextId) {
                        nextId = numericId + 1;
                    }
                } catch (IllegalArgumentException exception) {
                    System.out.println("Skipping invalid inventory record: " + line);
                }
            }
        } catch (IOException exception) {
            System.out.println("Could not load " + FILE_NAME + ". Starting with an empty inventory.");
        }
    }

    private void saveInventory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (GroceryItem item : items) {
                writer.write(item.getId() + "|" + item.getName() + "|"
                        + item.getQuantity() + "|" + item.getPrice());
                writer.newLine();
            }
        } catch (IOException exception) {
            System.out.println("Could not save inventory: " + exception.getMessage());
        }
    }

    private static class GroceryItem {
        private final String id;
        private final String name;
        private int quantity;
        private final double price;

        GroceryItem(String id, String name, int quantity, double price) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }

        String getId() {
            return id;
        }

        String getName() {
            return name;
        }

        int getQuantity() {
            return quantity;
        }

        void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        double getPrice() {
            return price;
        }
    }
}
