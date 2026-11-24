<div align="center">

# RUNGTA INTERNATIONAL SKILLS UNIVERSITY
## SCHOOL OF COMPUTER SCIENCE AND ENGINEERING

---

# PROJECT REPORT
## on
# GROCERY INVENTORY SYSTEM
### (Problem Statement No. 28)

**Programming Language:** Java

---

**Submitted by**

Name: `[Student Name]`
Enrollment No.: `[Enrollment Number]`
Course: `[B.Tech Computer Science and Engineering]`
Semester / Year: `[Semester / Year]`

**Under the guidance of**

`[Faculty / Mentor Name]`

**Academic Session:** `[Session, e.g. 2026–27]`

</div>

---

## Certificate

This is to certify that the project entitled **"Grocery Inventory System (PS No. 28)"** is a bonafide work carried out by **`[Student Name]`**, Enrollment No. **`[Enrollment Number]`**, in partial fulfilment of the requirements of the course **`[Course Name]`** at the School of Computer Science and Engineering, Rungta International Skills University, during the academic session **`[Session]`**.

The work is original and has not been submitted elsewhere for any other degree or diploma.

| | |
|---|---|
| **Project Guide:** `[Name]` | **Head of Department:** `[Name]` |
| Signature: ____________ | Signature: ____________ |
| Date: ____________ | Date: ____________ |

---

## Acknowledgement

I would like to express my sincere gratitude to my faculty mentor, `[Mentor Name]`, for the guidance and encouragement received throughout this project. I also thank the School of Computer Science and Engineering, Rungta International Skills University, for providing the opportunity and resources to apply the concepts learned in class to a practical problem. Finally, I thank my family and friends for their constant support.

`[Student Name]`

---

## Table of Contents

1. Abstract
2. Introduction
3. Problem Statement
4. Objectives
5. Existing System and Proposed System
6. System Requirements
7. System Design and Architecture
8. Explanation of Each Java Class
9. Algorithms and Working Logic
10. Implementation Details
11. Test Cases and Results
12. Advantages and Limitations
13. Future Scope
14. Conclusion

---

## 1. Abstract

The Grocery Inventory System is a console-based Java application that helps a small grocery store keep track of its stock. The user can add new items with a quantity and price, sell items (which reduces stock), view all items sorted alphabetically by name, and generate a low-stock report for items with fewer than five units. Every item is stored as an object in an `ArrayList`, and a `HashMap` provides quick lookup by item ID. Sorting is implemented manually using the Bubble Sort algorithm, and all data is stored in a text file (`inventory.txt`) using Java file handling so that records survive between program runs. The project uses only core Java and demonstrates classes and objects, collections, sorting logic, methods, exception handling and file input/output.

## 2. Introduction

Every shop needs to know what it has in stock. Doing this on paper is slow and error-prone: items are miscounted, stock-outs go unnoticed and sales may exceed the real quantity available. A small computer program can remove these problems by keeping accurate records and warning the shopkeeper when stock is running low.

This project builds such a program in Java for the university assignment PS No. 28. It is intentionally simple and console-based so that the main programming concepts – object-oriented design, collections, searching, sorting and file handling – can be clearly seen in the code.

## 3. Problem Statement

> Develop a console application to manage a grocery store's stock: add items with quantity and price, sell items (reduce stock), display items sorted by name and view a low-stock report. Data is saved to a file.
> Concepts: ArrayList, HashMap, Sorting, Methods, File Handling.
> Items are stored in `inventory.txt`. The first item ID generated is G101. Menu: 1-Add Item, 2-Sell Item, 3-Display Sorted by Name, 4-Low-Stock Report (quantity below 5).

## 4. Objectives

1. Store each inventory record as an object in an `ArrayList`.
2. Use a `HashMap` for quick lookup of items by ID.
3. Generate unique IDs automatically, starting from `G101`.
4. Implement add, sell, sorted-display and low-stock-report features using methods.
5. Write the sorting logic manually (no built-in sort methods).
6. Prevent selling more than the available stock and show *Out of stock* at quantity 0.
7. Load data from `inventory.txt` at start-up and save it on exit.
8. Validate user input and handle errors without crashing.

## 5. Existing System and Proposed System

### 5.1 Existing System
Many small stores still keep stock in a register or memory. Problems:
- Manual counting causes mistakes.
- No automatic warning for low stock.
- Searching for an item or sorting the list takes time.
- Records can be lost or damaged.
- Nothing prevents selling more than what is in stock.

### 5.2 Proposed System
The proposed system is a Java console application that:
- Stores items in memory (`ArrayList` + `HashMap`) and permanently in `inventory.txt`.
- Finds any item instantly by ID.
- Refuses sales larger than the available stock.
- Shows items alphabetically on demand.
- Lists low-stock items automatically.
- Validates every input.

## 6. System Requirements

**Hardware**
| Component | Minimum |
|---|---|
| Processor | Any dual-core processor |
| RAM | 2 GB |
| Storage | 50 MB free |

**Software**
| Component | Requirement |
|---|---|
| Operating System | Windows / Linux / macOS |
| Java | JDK 8 or higher (tested on JDK 21) |
| Editor / IDE | VS Code with Extension Pack for Java (or any terminal) |
| Version control | Git and GitHub (for submission) |

No external libraries, frameworks or databases are required.

## 7. System Design and Architecture

### 7.1 Layered structure

```
+-----------------------------------------------+
|  Main.java      (User interface layer)        |
|  - shows menu, reads and validates input      |
+-----------------------+-----------------------+
                        | calls
+-----------------------v-----------------------+
|  Inventory.java (Logic + data layer)          |
|  - ArrayList<Item>  : all items               |
|  - HashMap<String,Item> : lookup by ID        |
|  - add, sell, sort, report, load, save        |
+-----------------------+-----------------------+
                        | creates / uses
+-----------------------v-----------------------+
|  Item.java      (Model)                       |
|  - id, name, quantity, price                  |
+-----------------------------------------------+
                        |
                 inventory.txt (file storage)
```

### 7.2 Class relationship
`Main` **has an** `Inventory`; `Inventory` **has many** `Item` objects (composition through the `ArrayList` and `HashMap`).

### 7.3 Program flow

```
Start
  |
  v
Load inventory.txt  (skip if missing / empty)
  |
  v
+--> Show menu --> read choice
|        |
|   1 Add --> read name, qty, price --> generate ID --> store in ArrayList + HashMap
|   2 Sell --> read ID, qty --> HashMap lookup --> check stock --> reduce --> message
|   3 Display --> copy list --> Bubble Sort by name --> print
|   4 Low-stock --> loop items --> print qty < 5
|   5 Exit --> leave loop
|        |
+--------+ (choices 1-4 return to the menu)
  |
  v
Save inventory.txt --> End
```

### 7.4 File format
Each line of `inventory.txt` stores one item: `ID|name|quantity|price`, for example `G101|Rice|20|60`.

## 8. Explanation of Each Java Class

### 8.1 `Item.java`
Represents a single grocery item.

| Member | Purpose |
|---|---|
| `id, name, quantity, price` | Private fields (encapsulation) |
| Constructor | Creates an item with all details |
| Getters | Read the private fields |
| `reduceQuantity(int)` | Subtracts sold quantity from stock |
| `isOutOfStock()` | True when quantity is 0 |
| `getFormattedPrice()` | Prints `60` instead of `60.0` |
| `toDisplayString()` | Text for the screen: `G101 \| Rice \| Qty: 20 \| Price: 60` |
| `toFileLine()` | Text for the file: `G101\|Rice\|20\|60` |
| `fromFileLine(String)` | Static method that rebuilds an item from a file line; returns `null` for damaged lines |

### 8.2 `Inventory.java`
Contains all business logic.

| Member | Purpose |
|---|---|
| `items` (`ArrayList<Item>`) | Stores all items in insertion order |
| `itemMap` (`HashMap<String, Item>`) | Quick lookup by ID |
| `nextIdNumber` | Number used for the next ID (starts at 101) |
| `addItem(...)` | Creates ID, builds `Item`, stores in both collections |
| `sellItem(id, qty)` | Looks up the item, checks stock, reduces quantity, returns message |
| `displaySortedByName()` | Sorts a copy of the list and prints it |
| `bubbleSortByName(list)` | Manual Bubble Sort |
| `displayLowStockReport()` | Prints items with quantity below 5 |
| `loadFromFile()` | Reads `inventory.txt`, restores items and next ID |
| `saveToFile()` | Writes all items to `inventory.txt` |
| `updateNextIdNumber(id)` | Keeps new IDs unique after loading |

### 8.3 `Main.java`
Contains the `main` method and the user interface.

| Member | Purpose |
|---|---|
| `main()` | Loads data, runs the menu loop, saves on exit |
| `showMenu()` | Prints the menu |
| `addItem()`, `sellItem()` | Collect input for menu options 1 and 2 |
| `readLine`, `readName`, `readInt`, `readDouble` | Input helpers that keep asking until the input is valid |
| `saveAndExit()` | Saves the file and ends the program |

## 9. Algorithms and Working Logic

### 9.1 Automatic ID generation
```
nextIdNumber = 101
addItem:  id = "G" + nextIdNumber ; nextIdNumber = nextIdNumber + 1
```
After loading from a file, `nextIdNumber` is set to (largest numeric part of any loaded ID) + 1, so IDs never repeat after a restart.

### 9.2 Selling an item
```
item = itemMap.get(id)
if item == null              -> "Item not found"
else if sellQty > item.qty   -> "Insufficient stock. Available: <qty>"
else
     item.qty = item.qty - sellQty
     print "Sold <n> of <name>. Stock: <qty>"
     if item.qty == 0 append " (Out of stock)"
```
Time complexity of lookup: O(1) on average because of the `HashMap`.

### 9.3 Bubble Sort (by name)
```
copy = new ArrayList(items)            // do not disturb original order
for pass = 0 to n-2
    swapped = false
    for j = 0 to n-2-pass
        if name[j] > name[j+1] (ignoring case)
            swap copy[j] and copy[j+1]
            swapped = true
    if not swapped  -> stop (already sorted)
```
Time complexity: O(n²) in the worst case, O(n) when the list is already sorted. It is easy to understand and perfectly adequate for a small store.

### 9.4 Low-stock report
Loop through all items and print those with `quantity < 5`. Items with quantity 5 or more are not shown.

### 9.5 File load and save
- **Load:** if the file does not exist, start with an empty inventory. Otherwise read line by line, skip blank/damaged/duplicate lines, rebuild the items and update `nextIdNumber`.
- **Save:** write one `ID|name|quantity|price` line for every item in the list.

## 10. Implementation Details

- **Language/Version:** Java, compiled with `javac`, no external dependencies.
- **Collections:** `ArrayList<Item>` and `HashMap<String, Item>` are always kept in sync – every add puts the item in both.
- **Sorting:** done on a *copy* of the list, so the original order (the order in which items were added, which is also the saved file order) is not modified.
- **Input validation:** menu choices, numbers and names are validated; the helper methods loop until valid input is entered. Item IDs are converted to upper case so `g101` works like `G101`.
- **Names:** the `|` character is rejected in names because it is the file separator.
- **Exception handling:** `NumberFormatException` for bad numbers, `IOException` for file problems (a message is printed instead of crashing).
- **Safe exit:** if the input stream ends unexpectedly (for example Ctrl+D), the program still saves before exiting. Data is saved when the user chooses option 5.
- **Build and run:** `javac -d out src/*.java` then `java -cp out Main` from the project root.

## 11. Test Cases and Results

All tests were executed on the compiled program (JDK 21). Raw outputs are in `TEST_RESULTS.md`.

### 11.1 Assignment test cases

| # | Type | Input | Expected Output | Actual Output | Result |
|---|------|-------|-----------------|---------------|--------|
| 1 | Normal | Option 1: Rice, 20, 60 | `Item added. ID: G101 \| Rice \| Qty: 20 \| Price: 60` | Same | **Pass** |
| 2 | Boundary / minimum | Inventory `G101 \| Rice \| Qty: 5`; option 2: G101, 5 | `Sold 5 of Rice. Stock: 0 (Out of stock)` | Same | **Pass** |
| 3 | Invalid | Inventory `G101 \| Rice \| Qty: 20`; option 2: G101, 50 | `Insufficient stock. Available: 20` | Same | **Pass** |
| 4a | Different valid | Existing Rice (20, 60); option 1: Sugar, 3, 45 | `Item added. ID: G102 \| Sugar \| Qty: 3 \| Price: 45` | Same | **Pass** |
| 4b | | Option 3 | `1. G101 \| Rice \| Qty: 20 \| Price: 60` and `2. G102 \| Sugar \| Qty: 3 \| Price: 45` | Same | **Pass** |
| 4c | | Option 4 | `Low stock: G102 \| Sugar \| Qty: 3` | Same | **Pass** |

### 11.2 Additional tests

| # | Test | Result |
|---|------|--------|
| 5 | Add items, exit, restart: items restored and next ID continues (G103) | Pass |
| 6 | Items added out of order (Zucchini, Apple, Milk) are displayed as Apple, Milk, Zucchini | Pass |
| 7 | Invalid menu choices (`9`, `abc`, blank) | Pass – error message, menu shown again |
| 8 | Invalid quantity/price (`x`, `-3`, `free`, `-1`) | Pass – re-prompt |
| 9 | Selling an unknown ID | Pass – "Item not found" |
| 10 | Selling quantity 0 | Pass – rejected |
| 11 | Missing, empty, damaged or duplicate-ID lines in the file | Pass – safely skipped |
| 12 | Input ends abruptly | Pass – data still saved |
| 13 | Low-stock report when nothing is low | Pass – "No items are low on stock." |

## 12. Advantages and Limitations

### Advantages
- Simple, readable code that is easy to explain and extend.
- Fast ID lookup using `HashMap`.
- Data persists between runs.
- Overselling is impossible.
- Robust input validation and file handling.
- No external dependencies; runs anywhere Java runs.

### Limitations
- Console only (no graphical interface).
- Single user, no login or security.
- Bubble Sort is O(n²) – slow for very large inventories.
- Items cannot be edited, deleted or restocked from the menu.
- Data is saved only when the program exits normally (option 5, or end of input).
- Item names cannot contain the `|` character.
- No sales history, billing or reports beyond the low-stock list.

## 13. Future Scope

- Restock, edit and delete items; search by name or ID.
- Sales history, bills/invoices and revenue reports.
- Configurable low-stock threshold and automatic reorder suggestions.
- Expiry-date tracking for perishable goods.
- More efficient sorting (Merge Sort / Quick Sort) for large inventories.
- GUI using Swing or JavaFX, or a web interface.
- Database storage (MySQL/SQLite) and multi-user login.
- Barcode scanning support.

## 14. Conclusion

The Grocery Inventory System meets all requirements of PS No. 28. It stores each item as an object in an `ArrayList`, uses a `HashMap` for fast lookup, generates unique IDs starting from `G101`, prevents overselling, sorts items by name with a hand-written Bubble Sort, produces a low-stock report and keeps data safe in `inventory.txt` between runs. All four assignment test cases and the additional tests passed. The project strengthened my understanding of object-oriented design, collections, algorithms, exception handling and file handling in Java, and it provides a good base for future enhancements.

---

## References

1. Oracle, *Java Platform, Standard Edition Documentation* – https://docs.oracle.com/en/java/
2. Oracle, *The Java Tutorials* – https://docs.oracle.com/javase/tutorial/
3. Rungta International Skills University, *Project Description – PS No. 28*.
