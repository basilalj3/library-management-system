package project;

public class Main {
    public static void main(String[] args) {

        Library lib = new Library();

        // Add users
        lib.addUser(new Student("S001", "Alice", "CS"));
        lib.addUser(new Professor("P001", "Dr. Mark", "Engineering"));

        // Add items
        lib.addItem(new Book("B001", "Java Programming", 2020));
        lib.addItem(new Magazine("M001", "Tech Monthly", 2023));
        lib.addItem(new EBook("E001", "Algorithms", 2021, "download.com/e001"));

        // Borrow example
        try {
            Item item = lib.findItem("B001");
            Borrowable b = (Borrowable) item; // upcasting + polymorphism
            b.borrowItem("S001");
            System.out.println("Book borrowed successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Display sorted items via compareTo
        System.out.println("\nSorted Items:");
        for (Item i : lib.getSortedItems()) {
            System.out.println(i);
        }

        // Show notifications (log)
        System.out.println("\nActivity Log:");
        lib.printLogs();
    }
}
