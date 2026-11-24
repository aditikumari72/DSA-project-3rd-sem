# Test Results

Captured from the compiled program (JDK 21). Each test ran in a fresh folder with its own `inventory.txt`.
Menu text is filtered out of these logs to keep them short; "file:" lines show the saved `inventory.txt`.

```text
##### TEST CASE 1: Normal input (empty inventory, add Rice 20 @ 60)
Enter your choice: Item: Quantity: Price: Item added. ID: G101 | Rice | Qty: 20 | Price: 60
Enter your choice: Inventory saved to inventory.txt. Goodbye!
file: G101|Rice|20|60

##### TEST CASE 2: Boundary (G101 Rice Qty 5, sell 5)
Enter your choice: Item ID: Quantity: Sold 5 of Rice. Stock: 0 (Out of stock)
file: G101|Rice|0|60

##### TEST CASE 3: Invalid (G101 Rice Qty 20, sell 50)
Enter your choice: Item ID: Quantity: Insufficient stock. Available: 20
file: G101|Rice|20|60

##### TEST CASE 4: Different valid scenario
Loaded 1 item(s) from inventory.txt
Enter your choice: Item: Quantity: Price: Item added. ID: G102 | Sugar | Qty: 3 | Price: 45
Enter your choice: Sorted inventory:
1. G101 | Rice | Qty: 20 | Price: 60
2. G102 | Sugar | Qty: 3 | Price: 45
Enter your choice: Low stock: G102 | Sugar | Qty: 3
Enter your choice: Inventory saved to inventory.txt. Goodbye!
##### EXTRA A: Persistence + unique IDs after restart + sorting (added Zucchini, Apple, Milk across two runs)
Enter your choice: Item: Quantity: Price: Item added. ID: G101 | Zucchini | Qty: 10 | Price: 30.5
Enter your choice: Item: Quantity: Price: Item added. ID: G102 | Apple | Qty: 2 | Price: 120
file after run 1:
G101|Zucchini|10|30.5
G102|Apple|2|120
Loaded 2 item(s) from inventory.txt
1. Add Item
2. Sell Item
3. Display Items (Sorted by Name)
4. Low-Stock Report
5. Exit
Enter your choice: Item: Quantity: Price: Item added. ID: G103 | Milk | Qty: 8 | Price: 55
1. Add Item
2. Sell Item
3. Display Items (Sorted by Name)
4. Low-Stock Report
5. Exit
1. G102 | Apple | Qty: 2 | Price: 120
2. G103 | Milk | Qty: 8 | Price: 55
3. G101 | Zucchini | Qty: 10 | Price: 30.5
1. Add Item
2. Sell Item
3. Display Items (Sorted by Name)
4. Low-Stock Report
5. Exit
file after run 2:
G101|Zucchini|10|30.5
G102|Apple|2|120
G103|Milk|8|55

##### EXTRA B: Invalid input handling
Enter your choice: Invalid choice. Please enter a number from 1 to 5.
Enter your choice: Invalid choice. Please enter a number from 1 to 5.
Enter your choice: Invalid choice. Please enter a number from 1 to 5.
Enter your choice: Item: Item name cannot be empty. Try again.
Item: Quantity: Invalid input. Please enter a whole number.
Quantity: Value must be at least 0. Try again.
Quantity: Price: Invalid input. Please enter a number.
Price: Value must be at least 0.0. Try again.
Price: Item added. ID: G101 | Rice | Qty: 20 | Price: 60
Enter your choice: Item ID: Quantity: Item not found. No item with ID: G999
Enter your choice: Item ID: Quantity: Value must be at least 1. Try again.
Quantity: Sold 2 of Rice. Stock: 18
Enter your choice: Invalid choice. Please enter a number from 1 to 5.
Enter your choice: No items are low on stock.
Enter your choice: No items are low on stock.
Enter your choice: Inventory saved to inventory.txt. Goodbye!

##### EXTRA C: Empty file, corrupted lines, duplicate IDs
Loaded 1 item(s) from inventory.txt
Enter your choice: Item: Quantity: Price: Item added. ID: G106 | Coffee | Qty: 6 | Price: 200
Enter your choice: Low stock: G105 | Tea | Qty: 4
file:
G105|Tea|4|90
G106|Coffee|6|200

##### EXTRA D: Input ends abruptly (Ctrl+D) still saves
Enter your choice: 
Inventory saved to inventory.txt. Goodbye!
G101|Rice|20|60
```

## Summary

| Test | Result |
|------|--------|
| Test Case 1 – Normal input | PASS |
| Test Case 2 – Boundary / minimum | PASS |
| Test Case 3 – Invalid input | PASS |
| Test Case 4 – Different valid scenario (a, b, c) | PASS |
| Extra A – Persistence, unique IDs, sorting | PASS |
| Extra B – Invalid input handling | PASS |
| Extra C – Missing/empty/damaged file | PASS |
| Extra D – Input ends abruptly | PASS |
