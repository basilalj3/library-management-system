package project.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import project.model.ItemType;
import project.model.UserRole;
import project.repository.ItemRepository;
import project.repository.UserRepository;

/*
 Adds sample data the first time the app starts (when the database is empty).
 */
@Component
public class DataInitializer implements CommandLineRunner {
    private final LibraryService service;
    private final ItemRepository items;
    private final UserRepository users;

    public DataInitializer(LibraryService service, ItemRepository items, UserRepository users) {
        this.service = service;
        this.items = items;
        this.users = users;
    }

    @Override
    public void run(String... args) {
        if (users.count() == 0) {
            service.addUser(UserRole.STUDENT, "S001", "Alice", "CS");
            service.addUser(UserRole.PROFESSOR, "P001", "Dr. Mark", "Engineering");
        }
        if (items.count() == 0) {
            service.addItem(ItemType.BOOK, "B001", "Java Programming", 2020, null);
            service.addItem(ItemType.MAGAZINE, "M001", "Tech Monthly", 2023, null);
            service.addItem(ItemType.EBOOK, "E001", "Algorithms", 2021, "download.com/e001");
        }
    }
}
