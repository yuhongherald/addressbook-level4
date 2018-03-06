package seedu.address.logic.commands.exceptions;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 */
public class CommandWordException extends Exception {
    public CommandWordException(String message) {
        super(message);
    }
}
