# Library Management System (Spring Boot REST API)

A library management system built with **Java** and **Spring Boot**. It started as a console application for practicing **Object-Oriented Programming**, and I rebuilt it as a **REST API** with a database, a layered architecture, automated tests, and a simple web page.

## Features
- Manage library items of different types: **Books**, **Magazines**, and **E-Books**
- Manage users: **Students** and **Professors**
- Borrow and return items with validation (only the borrower can return an item)
- Item-specific **late-fee calculation** using polymorphism
- Items sorted alphabetically by title
- Activity log saved in the database with the time of every operation
- Clear error responses (404, 409, 400) with a message explaining the problem
- A web page to use the system from the browser

## Tech Stack
| Area | Technology |
|---|---|
| Language | Java 25 |
| Framework | Spring Boot 4 (Spring Web MVC, Validation) |
| Database | Spring Data JPA (Hibernate) with an H2 database saved to a file |
| Testing | JUnit 5, MockMvc |
| Build | Maven |

## Architecture
The project follows a **layered architecture**, where each layer has one job:

```
Request ─> Controller ─> Service ─> Repository ─> Database
           (HTTP)        (rules)     (data access)
```

| Layer | Package | Responsibility |
|---|---|---|
| Controller | `project.web` | Receives HTTP requests, validates input, returns JSON |
| Service | `project.service` | Business rules: borrowing, returning, late fees, activity log |
| Repository | `project.repository` | Reads and writes data using Spring Data JPA |
| Model | `project.model` | Entities and the OOP class hierarchy |
| Exceptions | `project.exception` | Custom exceptions, turned into HTTP responses by `ApiExceptionHandler` |

## Class Structure
```
Item (abstract)  ── implements Comparable, Cloneable
 ├── Book       ── implements Borrowable   (late fee: 0.5 / day)
 ├── Magazine   ── implements Borrowable   (late fee: 0.3 / day)
 └── EBook                                 (no late fee, cannot be borrowed)

User (abstract)
 ├── Student
 └── Professor

ActivityLog  ── one row per operation (message + time)
```

Items and users are each stored in one table using JPA **single-table inheritance**, with a column that records the subclass type.

## OOP Concepts Applied
| Concept | Where |
|---|---|
| **Abstraction** | Abstract classes `Item` and `User`, abstract method `calculateLateFee()` |
| **Inheritance** | `Book`, `Magazine`, `EBook` extend `Item`; `Student`, `Professor` extend `User` |
| **Polymorphism** | Each item type overrides `calculateLateFee()` and `toString()` |
| **Interfaces** | `Borrowable`, implemented only by physical items (`Book`, `Magazine`) |
| **Encapsulation** | Protected and private fields with getter methods |
| **Custom Exceptions** | `ItemNotAvailableException`, `InvalidReturnException`, `ResourceNotFoundException`, and more |
| **Enums** | `ItemType`, `UserRole` |
| **Object Methods** | Overridden `equals()`, `hashCode()`, `clone()`, `compareTo()`, `toString()` |

## How to Run
**Requirements:** Java 25 (Maven is included through the Maven Wrapper)

```bash
git clone https://github.com/basilalj3/library-management-system.git
cd library-management-system
./mvnw spring-boot:run
```
On Windows, use `mvnw.cmd spring-boot:run`.

Then open:
- **Web page:** http://localhost:8080
- **API:** http://localhost:8080/api/items
- **Database console:** http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:file:./data/library`, user: `sa`, no password)

Sample users and items are added automatically the first time the app starts.

## API Endpoints
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/items` | List all items, sorted by title |
| GET | `/api/items/{id}` | Get one item |
| POST | `/api/items` | Add an item |
| POST | `/api/items/{id}/borrow?userId=S001` | Borrow an item |
| POST | `/api/items/{id}/return?userId=S001` | Return an item |
| GET | `/api/items/{id}/late-fee?days=5` | Calculate the late fee |
| GET | `/api/users` | List all users |
| GET | `/api/users/{id}` | Get one user |
| POST | `/api/users` | Add a user |
| GET | `/api/logs` | Show the activity log, newest first |

**Example: add a book**
```json
POST /api/items
{ "type": "BOOK", "id": "B002", "title": "Clean Code", "year": 2008 }
```

**Example: add a student**
```json
POST /api/users
{ "role": "STUDENT", "id": "S002", "name": "Sara", "departmentOrFaculty": "IT" }
```

**Example error: borrowing a book that is already borrowed** returns `409 Conflict`
```json
{ "status": 409, "detail": "Book not available." }
```

## Tests
The project has **21 automated tests**:
- `ItemTest` (9 tests): late fees, borrowing and returning rules, `equals()`/`hashCode()`, sorting, and cloning
- `LibraryApiTest` (12 tests): the full API through HTTP, including error cases (404, 409, 400)

Tests use a separate in-memory database, so they never change the real data.

```bash
./mvnw test
```

## Project Structure
```
src/main/java/project
 ├── LibraryApplication.java   Spring Boot entry point
 ├── web/                      Controller, request records, error handler
 ├── service/                  LibraryService, sample data
 ├── repository/               Spring Data JPA repositories
 ├── model/                    Entities (Item, Book, User, ActivityLog, ...)
 └── exception/                Custom exceptions
src/main/resources
 ├── application.properties    Database and server settings
 └── static/index.html         Web page
src/test/java/project          Unit and API tests
```

## Author
**Basil Aljabri**, Computer Science Student, Umm Al-Qura University
GitHub: [@basilalj3](https://github.com/basilalj3) | LinkedIn: [basil-aljabri](https://www.linkedin.com/in/basil-aljabri)
