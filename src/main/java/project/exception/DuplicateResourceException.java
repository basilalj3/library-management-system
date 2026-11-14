package project.exception;

/* Thrown when adding an item or user whose id already exists. (HTTP 409) */
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String msg) {
        super(msg);
    }
}
