package project.exception;

public class ItemInUseException extends RuntimeException {
    public ItemInUseException(String message) {
        super(message);
    }
}
