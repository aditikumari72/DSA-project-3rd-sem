# Grocery Inventory System

## Project structure

```text
DSA-project-3rd-sem/
├── GroceryInventorySystem.java
├── inventory.txt
└── README.md
```

The application is implemented in one Java source file. It uses an `ArrayList`
to keep grocery records and a `HashMap` to find items by ID. Inventory data is
read from and saved to `inventory.txt` in the directory where the program runs.

## Features

- Add items with automatically generated IDs beginning at `G101`.
- Sell items by ID without allowing sales beyond available stock.
- Display items alphabetically using a manually implemented Bubble Sort.
- Report items with fewer than five units in stock.
- Validate names, quantities, prices, and menu selections.
- Skip malformed or duplicate records when loading the inventory file.
- Save inventory when exiting and load it automatically at startup.

The inventory file uses one record per line in this format:

```text
ID|Name|Quantity|Price
```

## Requirements

- Java Development Kit (JDK) installed and available on your `PATH`.
- No external libraries or databases are required.

## Compile and run

Open a terminal in the project directory and run:

```sh
javac GroceryInventorySystem.java
java GroceryInventorySystem
```

Choose **5. Exit** to save changes to `inventory.txt`.

## Sample console output

```text
=== Grocery Inventory System ===
1. Add Item
2. Sell Item
3. Display Items (Sorted by Name)
4. Low-Stock Report
5. Exit
Enter your choice: 3
ID       Name                 Quantity   Price
G102     Apples               4          $1.50
G101     Rice                 12         $8.99
G103     Tea                  2          $3.25

Enter your choice: 4
Low-stock items (quantity below 5):
G102 - Apples: 4
G103 - Tea: 2
```

## Test cases

Run the application and verify each scenario. The sample inventory includes
records suitable for exercising the reports and sale validation.

1. **Add item:** Choose `1`, enter `Milk`, quantity `8`, and price `2.75`.
   Confirm it receives the next available ID (`G104` with the supplied sample
   data), then exit and confirm it is written to `inventory.txt`.
2. **Sell item and stock limit:** Sell `3` units of `G101` and confirm the
   quantity decreases. Try selling more than its remaining quantity and confirm
   the stock is unchanged. Selling the remaining stock should display
   `Out of Stock`.
3. **Alphabetical display:** Choose `3` and confirm names appear alphabetically,
   regardless of their order in `inventory.txt`.
4. **Low-stock report and persistence:** Choose `4` and confirm only items with
   fewer than five units are listed. Exit and restart; confirm the saved
   quantities and newly added item are loaded.
