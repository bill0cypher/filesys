package exceptions;

public class EmptyBodyException extends Exception {
    public static final String DEFAULT_MESSAGE_TEXT = "Data wasn't provided.";

    public EmptyBodyException(String message) {
        super(message);
    }

    public EmptyBodyException(String message, Throwable cause) {
        super(message, cause);
    }
}
