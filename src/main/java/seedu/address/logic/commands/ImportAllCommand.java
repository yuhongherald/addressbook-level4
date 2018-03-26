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
 * Attempts to import all (@code JobEntry) into Servicing Manager
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
            throw new CommandException("Excel file first row headers are not defined properly. "
                    + "Type 'help' to read more.");
        }
        try {
            importSession.reviewAllRemainingJobEntries(true);
            importSession.closeSession();
        } catch (DataIndexOutOfBoundsException e) {
            throw new CommandException("Excel file has bad format. Try copying the cell values into a new excel file "
                    + "before trying again");
        } catch (IOException e) {
            throw new CommandException("Unable to export file. Please close the application and try again.");
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
