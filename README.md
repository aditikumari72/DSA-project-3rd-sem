# Grocery Inventory System

A console-based Java application that helps a grocery store manage its stock: add items, sell items, view items sorted by name, and see a low-stock report. Data is saved in a text file and reloaded on the next run.

**Problem Statement No.:** PS No. 28
**University:** Rungta International Skills University – School of Computer Science and Engineering
**Language:** Java (core Java only – no libraries, no frameworks, no database)

---

## 1. Project Objectives

- Store every grocery item as an **object** inside an `ArrayList`.
- Use a `HashMap` for **fast lookup by item ID**.
- Add items with auto-generated unique IDs (`G101`, `G102`, ...).
- Sell items and **never allow selling more than the available stock**.
- Display items **sorted by name** using a **manually written** sorting algorithm.
- Show a **low-stock report** (quantity below 5).
- **Save** data to `inventory.txt` on exit and **load** it on start.

## 2. Features

| # | Feature | Description |
|---|---------|-------------|
| 1 | Add Item | Asks for name, quantity, price; generates the next ID; confirms the addition. |
| 2 | Sell Item | Asks for ID and quantity; reduces stock; shows `Out of stock` at 0; rejects oversell. |
| 3 | Display Sorted | Prints all items alphabetically by name (Bubble Sort, original order unchanged). |
| 4 | Low-Stock Report | Lists items whose quantity is below 5. |
| 5 | Exit | Saves everything to `inventory.txt` and closes. |

Extra safety: invalid menu choices, non-numeric input, negative numbers, empty names, unknown IDs, a missing/empty/damaged `inventory.txt`, and unexpected end of input are all handled safely.

## 3. Technologies Used

- Java (JDK 8 or newer; tested with JDK 21)
- Visual Studio Code (with the *Extension Pack for Java*) or any terminal
- Git / GitHub for version control

## 4. Java Concepts Implemented

- Classes and objects, constructors, encapsulation (private fields + getters)
- Methods (including `static` helper methods and one `static` factory method)
- `ArrayList` and `HashMap` (Collections)
- Manual Bubble Sort with `String` comparison
- `switch` statement, `while` loops, `if/else`
- Exception handling (`try / catch`, `NumberFormatException`, `IOException`)
- File handling with `BufferedReader`, `FileReader`, `BufferedWriter`, `FileWriter`
- Input handling with `Scanner`

## 5. Project Folder Structure

```
GroceryInventorySystem/
│
├── src/
│   ├── Main.java          # Menu + user input handling
│   ├── Item.java          # One grocery item (data + small behaviours)
│   └── Inventory.java     # ArrayList, HashMap, sorting, file load/save
│
├── inventory.txt          # Saved data (empty at first)
├── README.md              # This file
├── PROJECT_REPORT.md      # Full academic report
├── TEST_RESULTS.md        # Real output of all test runs
├── .gitignore
└── screenshots/
    └── README.md          # Guide for adding your own screenshots
```

## 6. Prerequisites

1. **JDK 8 or higher** – check with `java -version` and `javac -version`.
2. **VS Code** with the **Extension Pack for Java** (optional but recommended).

## 7. Installation and Setup

1. Unzip `GroceryInventorySystem_PS28.zip`.
2. Open VS Code → **File → Open Folder…** → select the `GroceryInventorySystem` folder.
3. Open a terminal in VS Code (**Terminal → New Terminal**). Make sure the terminal is inside the **project root** (the folder that contains `src` and `inventory.txt`).

> Important: run the program from the project root so that `inventory.txt` is found and saved in the right place.

## 8. How to Compile

```bash
javac -d out src/*.java
```

(On Windows PowerShell, if `src/*.java` is not expanded, use: `javac -d out src\Main.java src\Item.java src\Inventory.java`)

## 9. How to Run

```bash
java -cp out Main
```

Using the VS Code *Run* button on `Main.java` also works when the Java extension pack is installed (the working directory is the project root by default).

## 10. Sample Console Output

```
========================================
       GROCERY INVENTORY SYSTEM
========================================

1. Add Item
2. Sell Item
3. Display Items (Sorted by Name)
4. Low-Stock Report
5. Exit

Enter your choice: 1
Item: Rice
Quantity: 20
Price: 60
Item added. ID: G101 | Rice | Qty: 20 | Price: 60

Enter your choice: 1
Item: Sugar
Quantity: 3
Price: 45
Item added. ID: G102 | Sugar | Qty: 3 | Price: 45

Enter your choice: 3
Sorted inventory:
1. G101 | Rice | Qty: 20 | Price: 60
2. G102 | Sugar | Qty: 3 | Price: 45

Enter your choice: 4
Low stock: G102 | Sugar | Qty: 3

Enter your choice: 2
Item ID: G101
Quantity: 50
Insufficient stock. Available: 20

Enter your choice: 2
Item ID: G102
Quantity: 3
Sold 3 of Sugar. Stock: 0 (Out of stock)

Enter your choice: 5
Inventory saved to inventory.txt. Goodbye!
```

Format of `inventory.txt` (one item per line: `ID|name|quantity|price`):

```
G101|Rice|20|60
G102|Sugar|0|45
```

## 11. Concepts Explained

**ArrayList** – a resizable list. Every `Item` object is added to `ArrayList<Item> items`, which keeps the items in the order they were added and lets us loop through all of them (for display, low-stock report and saving).

**HashMap** – stores key-value pairs. Here the key is the item ID (e.g. `"G101"`) and the value is the `Item`. `itemMap.get("G101")` finds an item directly, without looping through the whole list, which makes selling fast.

**OOP (Object-Oriented Programming)** – the program is split into classes with clear jobs: `Item` (one product), `Inventory` (manages all products and the file) and `Main` (user interface). Fields are `private` (encapsulation) and accessed through methods.

**Manual sorting (Bubble Sort)** – a copy of the list is sorted by repeatedly comparing neighbouring items by name and swapping them when they are in the wrong order. After each pass the largest name moves to the end. If a pass makes no swaps, sorting stops early. `Collections.sort()` and `Arrays.sort()` are **not** used. Sorting a *copy* keeps the original inventory order unchanged.

**File handling** – on start, `BufferedReader` reads `inventory.txt` line by line and rebuilds the items; on exit, `BufferedWriter` writes one line per item. Missing, empty, blank or damaged lines are skipped safely. After loading, the next ID number is set to (highest existing ID number + 1), so IDs stay unique after restarting.

## 12. Test Cases

| # | Scenario | Input | Expected / Actual Output | Result |
|---|----------|-------|--------------------------|--------|
| 1 | Normal input | Add `Rice`, 20, 60 | `Item added. ID: G101 \| Rice \| Qty: 20 \| Price: 60` | PASS |
| 2 | Boundary | Have `G101 Rice Qty 5`; sell 5 | `Sold 5 of Rice. Stock: 0 (Out of stock)` | PASS |
| 3 | Invalid input | Have `G101 Rice Qty 20`; sell 50 | `Insufficient stock. Available: 20` | PASS |
| 4a | Different valid | Add `Sugar`, 3, 45 | `Item added. ID: G102 \| Sugar \| Qty: 3 \| Price: 45` | PASS |
| 4b | | Display sorted | `1. G101 \| Rice ...` / `2. G102 \| Sugar ...` | PASS |
| 4c | | Low-stock report | `Low stock: G102 \| Sugar \| Qty: 3` | PASS |
| 5 | Persistence | Add items, exit, restart, add one more | Items reloaded; new ID continues (G103) | PASS |
| 6 | Bad input | Menu `9`, `abc`; quantity `-3`, `x` | Clear error message and re-prompt | PASS |
| 7 | Missing / empty / damaged file | Start with no file or bad lines | No crash; bad lines skipped | PASS |

The complete captured output of every test run is in [`TEST_RESULTS.md`](TEST_RESULTS.md).

## 13. Future Improvements

- Update/delete items and restock (add stock to an existing item).
- Search items by name.
- Track sales history, total revenue and daily reports.
- Configurable low-stock limit.
- Expiry dates for perishable items.
- A GUI (Swing/JavaFX) or a database in a later version.

## 14. Author Information

| Field | Details |
|-------|---------|
| Student Name | `[Your Name]` |
| Enrollment Number | `[Your Enrollment Number]` |
| Course | `[B.Tech Computer Science and Engineering]` |
| University | Rungta International Skills University |
| Problem Statement | PS No. 28 – Grocery Inventory System |
