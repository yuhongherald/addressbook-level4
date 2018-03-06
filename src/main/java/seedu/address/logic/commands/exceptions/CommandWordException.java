package seedu.address.logic.commands.exceptions;

import seedu.address.logic.commands.CommandWords;

/**
 * Represents an error which occurs during execution of {@link SetCommand}.
 */
public class CommandWordException extends Exception {
    public CommandWordException(String message) {
        super(message);
    }
}
