package general.exceptions;

/**
 * Is throwed when collection is empty.
 */
public class CommandUsageException extends Exception{
    public CommandUsageException() {
        super();
    }

    public CommandUsageException(String message) {
        super(message);
    }
}
