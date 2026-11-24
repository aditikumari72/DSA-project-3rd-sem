import java.util.Scanner;

/**
 * Main.java
 * Console menu for the Grocery Inventory System (PS No. 28).
 * Shows the menu repeatedly, validates input and calls Inventory methods.
 */
public class Main {

    private static final String DATA_FILE = "inventory.txt";
    private static Scanner scanner = new Scanner(System.in);
    private static Inventory inventory = new Inventory(DATA_FILE);

    public static void main(String[] args) {
        int loaded = inventory.loadFromFile(); // Load saved data at start
        if (loaded > 0) {
            System.out.println("Loaded " + loaded + " item(s) from " + DATA_FILE);
        }

        boolean running = true;
        while (running) {
            showMenu();
            String choice = readLine("Enter your choice: ");

            switch (choice) {
                case "1":
                    addItem();
                    break;
                case "2":
                    sellItem();
                    break;
                case "3":
                    inventory.displaySortedByName();
                    break;
                case "4":
                    inventory.displayLowStockReport();
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 5.");
            }
        }

        saveAndExit();
    }

    /** Prints the main menu. */
    private static void showMenu() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("       GROCERY INVENTORY SYSTEM");
        System.out.println("========================================");
        System.out.println();
        System.out.println("1. Add Item");
        System.out.println("2. Sell Item");
        System.out.println("3. Display Items (Sorted by Name)");
        System.out.println("4. Low-Stock Report");
        System.out.println("5. Exit");
        System.out.println();
    }

    /** Menu option 1: asks for details and adds a new item. */
    private static void addItem() {
        String name = readName("Item: ");
        int quantity = readInt("Quantity: ", 0);
        double price = readDouble("Price: ", 0);

        Item item = inventory.addItem(name, quantity, price);
        System.out.println("Item added. ID: " + item.getId() + " | " + item.getName()
                + " | Qty: " + item.getQuantity() + " | Price: " + item.getFormattedPrice());
    }

    /** Menu option 2: asks for an ID and quantity and sells that much stock. */
    private static void sellItem() {
        String id = readLine("Item ID: ").toUpperCase(); // so "g101" also works
        int quantity = readInt("Quantity: ", 1);
        System.out.println(inventory.sellItem(id, quantity));
    }

    // ---------- Input helper methods (they repeat until the input is valid) ----------

    /** Reads one line of text. If the input ends (no more lines), it saves and exits. */
    private static String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            System.out.println();
            saveAndExit();
        }
        return scanner.nextLine().trim();
    }

    /** Reads a non-empty item name (the '|' character is not allowed). */
    private static String readName(String prompt) {
        while (true) {
            String name = readLine(prompt);
            if (name.isEmpty()) {
                System.out.println("Item name cannot be empty. Try again.");
            } else if (name.contains("|")) {
                System.out.println("Item name cannot contain the '|' character. Try again.");
            } else {
                return name;
            }
        }
    }

    /** Reads a whole number that is at least 'minimum'. */
    private static int readInt(String prompt, int minimum) {
        while (true) {
            String text = readLine(prompt);
            try {
                int value = Integer.parseInt(text);
                if (value >= minimum) {
                    return value;
                }
                System.out.println("Value must be at least " + minimum + ". Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    /** Reads a decimal number that is at least 'minimum'. */
    private static double readDouble(String prompt, double minimum) {
        while (true) {
            String text = readLine(prompt);
            try {
                double value = Double.parseDouble(text);
                if (Double.isNaN(value) || Double.isInfinite(value)) {
                    System.out.println("Invalid input. Please enter a valid number.");
                } else if (value >= minimum) {
                    return value;
                } else {
                    System.out.println("Value must be at least " + minimum + ". Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    /** Saves the inventory to the file and ends the program. */
    private static void saveAndExit() {
        if (inventory.saveToFile()) {
            System.out.println("Inventory saved to " + DATA_FILE + ". Goodbye!");
        }
        System.exit(0);
    }
}
