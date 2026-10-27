package project;

import project.ItemNotAvailableException;


public class Book extends Item implements Borrowable {
    private String borrowedById; // null = available

    public Book() {
        super();
    }

    public Book(String id, String title, int year) {
        super(id, title, year, ItemType.BOOK);
    }

    @Override
    public double calculateLateFee(int daysLate) {
        return daysLate * 0.5;
    }

    @Override
    public void borrowItem(String userId) throws ItemNotAvailableException {
        if (borrowedById != null) {
            throw new ItemNotAvailableException("Book not available.");
        }
        borrowedById = userId;
    }

    @Override
    public void returnItem(String userId) throws Exception {
        if (borrowedById == null) {
            throw new Exception("Book is not borrowed.");
        }

        if (!borrowedById.equals(userId)) {
            throw new Exception("This user did not borrow the book.");
        }

        borrowedById = null;  // book is now available
    }

    @Override
    public boolean isAvailable() {
        return borrowedById == null;
    }

    @Override
    public String toString() {
        String status;
        if (borrowedById == null) {
            status = "Available";
        } else {
            status = "Borrowed";
        }

        return "Book: " + super.toString() + " [" + status + "]";
    }
}



