package project.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.exception.InvalidReturnException;
import project.exception.ItemNotAvailableException;
import project.model.ActivityLog;
import project.model.Item;
import project.model.User;
import project.service.LibraryService;

import java.util.List;
import java.util.Map;

/*
 REST API for the library. Only handles HTTP; the logic is in LibraryService.
 */
@RestController
@RequestMapping("/api")
@Validated
public class LibraryController {
    private final LibraryService service;

    public LibraryController(LibraryService service) {
        this.service = service;
    }

    // ---- Items ----

    @GetMapping("/items")
    public List<Item> items() {
        return service.getSortedItems();
    }

    @GetMapping("/items/{id}")
    public Item item(@PathVariable String id) {
        return service.getItem(id);
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public Item addItem(@Valid @RequestBody ItemRequest r) {
        return service.addItem(r.type(), r.id(), r.title(), r.year(), r.downloadLink());
    }
    @DeleteMapping("/items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable String id) {
        service.deleteItem(id);
    }

    @PostMapping("/items/{id}/borrow")
    public Item borrow(@PathVariable String id, @RequestParam String userId) throws ItemNotAvailableException {
        return service.borrow(id, userId);
    }

    @PostMapping("/items/{id}/return")
    public Item returnItem(@PathVariable String id, @RequestParam String userId) throws InvalidReturnException {
        return service.returnItem(id, userId);
    }

    @GetMapping("/items/{id}/late-fee")
    public Map<String, Object> lateFee(@PathVariable String id, @RequestParam @Min(0) int days) {
        return Map.of("itemId", id, "daysLate", days, "fee", service.lateFee(id, days));
    }

    // ---- Users ----

    @GetMapping("/users")
    public List<User> users() {
        return service.getUsers();
    }

    @GetMapping("/users/{id}")
    public User user(@PathVariable String id) {
        return service.getUser(id);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@Valid @RequestBody UserRequest r) {
        return service.addUser(r.role(), r.id(), r.name(), r.departmentOrFaculty());
    }

    // ---- Activity log ----

    @GetMapping("/logs")
    public List<ActivityLog> logs() {
        return service.getLogs();
    }
}
