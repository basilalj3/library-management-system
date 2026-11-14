package project.exception;

/* Thrown when an item or user id does not exist. (HTTP 404) */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
