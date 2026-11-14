package project.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import project.exception.InvalidReturnException;
import project.exception.ItemNotAvailableException;

@Entity
@DiscriminatorValue("BOOK")
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
    public void returnItem(String userId) throws InvalidReturnException {
        if (borrowedById == null) {
            throw new InvalidReturnException("Book is not borrowed.");
        }

        if (!borrowedById.equals(userId)) {
            throw new InvalidReturnException("This user did not borrow the book.");
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
