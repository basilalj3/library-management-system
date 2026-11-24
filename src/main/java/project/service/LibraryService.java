package project.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.exception.*;
import project.model.*;
import project.repository.ActivityLogRepository;
import project.repository.ItemRepository;
import project.repository.UserRepository;

import java.util.Collections;
import java.util.List;

/*
 Business logic of the library. Knows nothing about HTTP:
 it throws the project's own exceptions, and ApiExceptionHandler
 turns them into HTTP responses.
 */
@Service
@Transactional
public class LibraryService {
    private final ItemRepository items;
    private final UserRepository users;
    private final ActivityLogRepository logs;

    public LibraryService(ItemRepository items, UserRepository users, ActivityLogRepository logs) {
        this.items = items;
        this.users = users;
        this.logs = logs;
    }

    // ---- Items ----

    @Transactional(readOnly = true)
    public List<Item> getSortedItems() {
        List<Item> all = items.findAll();
        Collections.sort(all); // uses Item.compareTo (by title)
        return all;
    }
    @Transactional(readOnly = true)
    public List<Item> getItemsByYear(int year) {
        List<Item> result = items.findByYear(year);
        Collections.sort(result);
        return result;
    }

    @Transactional(readOnly = true)
    public Item getItem(String id) {
        return items.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found: " + id));
    }

    public Item addItem(ItemType type, String id, String title, int year, String downloadLink) {
        if (items.existsById(id)) {
            throw new DuplicateResourceException("Item already exists: " + id);
        }
        Item item = switch (type) {
            case BOOK -> new Book(id, title, year);
            case MAGAZINE -> new Magazine(id, title, year);
            case EBOOK -> new EBook(id, title, year, downloadLink);
        };
        items.save(item);
        log("Added item: " + title);
        return item;
    }
    public void deleteItem(String id) {
        Item item = getItem(id);
        if (item instanceof Borrowable b && !b.isAvailable()) {
            throw new ItemInUseException("Cannot delete a borrowed item: " + id);
        }
        items.deleteById(id);
        log("Deleted item: " + item.getTitle());
    }

    public Item borrow(String itemId, String userId) throws ItemNotAvailableException {
        User user = getUser(userId);
        Item item = getItem(itemId);
        asBorrowable(item).borrowItem(userId);
        log(user.getName() + " borrowed: " + item.getTitle());
        return item; // changes are saved automatically at the end of the transaction
    }

    public Item returnItem(String itemId, String userId) throws InvalidReturnException {
        User user = getUser(userId);
        Item item = getItem(itemId);
        asBorrowable(item).returnItem(userId);
        log(user.getName() + " returned: " + item.getTitle());
        return item;
    }

    @Transactional(readOnly = true)
    public double lateFee(String itemId, int daysLate) {
        return getItem(itemId).calculateLateFee(daysLate);
    }

    // ---- Users ----

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return users.findAll(Sort.by("id"));
    }

    @Transactional(readOnly = true)
    public User getUser(String id) {
        return users.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    public User addUser(UserRole role, String id, String name, String departmentOrFaculty) {
        if (users.existsById(id)) {
            throw new DuplicateResourceException("User already exists: " + id);
        }
        User user = switch (role) {
            case STUDENT -> new Student(id, name, departmentOrFaculty);
            case PROFESSOR -> new Professor(id, name, departmentOrFaculty);
        };
        users.save(user);
        log("Added user: " + name);
        return user;
    }

    // ---- Activity log ----

    @Transactional(readOnly = true)
    public List<ActivityLog> getLogs() {
        return logs.findAllByOrderByIdDesc();
    }

    private void log(String message) {
        logs.save(new ActivityLog(message));
    }

    private Borrowable asBorrowable(Item item) {
        if (item instanceof Borrowable b) {
            return b;
        }
        throw new ItemNotBorrowableException(item.getType() + " items cannot be borrowed.");
    }
}
