package seedu.address.model.session.exceptions;

//@@author yuhongherald
/**
 * Represents an error which occurs during initialization of a {@link seedu.address.model.session.ImportSession}.
 */
public class InitializeSessionException extends Exception {
    public InitializeSessionException(String message) {
        super(message);
    }
}
