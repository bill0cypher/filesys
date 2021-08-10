package exceptions;

public class EmptyListException extends Exception {
    public static final String DEFAULT_MESSAGE_TEXT = "No entries of type: %1$s";

    public EmptyListException(String message) {
        super(message);
    }

    public EmptyListException(String message, Throwable cause) {
        super(message, cause);
    }
}
