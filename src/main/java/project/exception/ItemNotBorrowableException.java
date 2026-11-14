package project.exception;

/* Thrown when trying to borrow/return an item that is not Borrowable (e.g. an EBook). (HTTP 400) */
public class ItemNotBorrowableException extends RuntimeException {
    public ItemNotBorrowableException(String msg) {
        super(msg);
    }
}
