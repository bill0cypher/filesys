package exceptions;

public class NoSuchEntryException extends Exception {
    public static final String DEFAULT_MESSAGE_TEXT = "No entry found by id %1$s";

    public NoSuchEntryException(String message) {
        super(message);
    }

    public NoSuchEntryException(String message, Throwable cause) {
        super(message, cause);
    }
}

