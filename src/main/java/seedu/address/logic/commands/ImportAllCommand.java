package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.session.ImportSession;
import seedu.address.model.session.exceptions.DataIndexOutOfBoundsException;
import seedu.address.model.session.exceptions.FileAccessException;
import seedu.address.model.session.exceptions.FileFormatException;

//@@author yuhongherald
/**
 *
 */
public class ImportAllCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "importAll";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a command word to user preference. "
            + "Parameters: CURRENT_COMMAND_WORD NEW_COMMAND_WORD"
            + "Example: " + "set" + " "
            + "OLD_COMMAND" + "NEW_COMMAND";

    public static final String MESSAGE_SUCCESS = "%s has been imported, with %d job entries!";

    private final String filePath;

    public ImportAllCommand(String filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        ImportSession importSession = ImportSession.getInstance();
        try {
            importSession.initializeSession(filePath);
        } catch (FileAccessException e) {
            e.printStackTrace();
        } catch (FileFormatException e) {
            e.printStackTrace();
        }
        // write import all command
        try {
            importSession.closeSession();
        } catch (DataIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportAllCommand // instanceof handles nulls
                && filePath.equals(((ImportAllCommand) other).filePath));
    }

}
