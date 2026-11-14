package project.exception;

/* Thrown when someone tries to borrow an item that is already borrowed. (HTTP 409) */
public class ItemNotAvailableException extends Exception {
    public ItemNotAvailableException(String msg) {
        super(msg);
    }
}
