package project.exception;

/*
 Thrown when an item cannot be returned: it is not borrowed,
 or a different user borrowed it. (HTTP 400)
 */
public class InvalidReturnException extends Exception {
    public InvalidReturnException(String msg) {
        super(msg);
    }
}
