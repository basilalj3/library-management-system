# Library Management System (Java OOP)

A console-based library management system built in **Java**, designed to demonstrate clean **Object-Oriented Programming** design: abstraction, inheritance, polymorphism, and encapsulation.

## Features
- Manage library items of different types: **Books**, **Magazines**, and **E-Books**
- Manage users: **Students** and **Professors**
- Borrow and return items with validation (only the borrower can return an item)
- Item-specific **late-fee calculation** using polymorphism
- Items automatically sorted alphabetically by title
- Activity log that records every operation in the system

## OOP Concepts Applied
| Concept | Where |
|---|---|
| **Abstraction** | Abstract classes `Item` and `User` with abstract method `calculateLateFee()` |
| **Inheritance** | `Book`, `Magazine`, `EBook` extend `Item`; `Student`, `Professor` extend `User` |
| **Polymorphism** | Each item type overrides `calculateLateFee()` and `toString()` |
| **Interfaces** | `Borrowable` interface implemented only by physical items (Book, Magazine) |
| **Encapsulation** | Private/protected fields with getter methods |
| **Custom Exceptions** | `ItemNotAvailableException` thrown when borrowing an unavailable item |
| **Collections** | `ArrayList` for storage, `TreeSet` + `Comparable` for sorting |
| **Enums** | `ItemType` (BOOK, MAGAZINE, EBOOK) |
| **Inner Classes** | `Library.Notification` for activity logging |
| **Object Methods** | Overridden `equals()`, `clone()`, `compareTo()`, `toString()` |

## Class Structure
```
Item (abstract)  ── implements Comparable, Cloneable
 ├── Book       ── implements Borrowable   (late fee: 0.5 / day)
 ├── Magazine   ── implements Borrowable   (late fee: 0.3 / day)
 └── EBook                                 (no late fee)

User (abstract)
 ├── Student
 └── Professor

Library  ── manages items, users, and logs (inner class: Notification)
```

## How to Run
**Requirements:** Java 8 or later

```bash
git clone https://github.com/basilalj3/library-management-system.git
cd library-management-system
javac -d out src/project/*.java
java -cp out project.Main
```

## Sample Output
```
Book borrowed successfully!

Sorted Items:
EBook: Algorithms (ID: E001, 2021) [Download link: download.com/e001]
Book: Java Programming (ID: B001, 2020) [Borrowed]
Magazine: Tech Monthly (ID: M001, 2023) [Available]

Activity Log:
NOTIFICATION: Added user: Alice
NOTIFICATION: Added user: Dr. Mark
NOTIFICATION: Added item: Java Programming
NOTIFICATION: Added item: Tech Monthly
NOTIFICATION: Added item: Algorithms
```

## Author
**Basil Aljabri** — Computer Science Student, Umm Al-Qura University
GitHub: [@basilalj3](https://github.com/basilalj3)
