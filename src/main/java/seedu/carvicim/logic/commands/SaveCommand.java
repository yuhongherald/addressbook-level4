package seedu.carvicim.logic.commands;

import java.io.IOException;

import seedu.carvicim.logic.commands.exceptions.CommandException;
import seedu.carvicim.storage.session.ImportSession;
import seedu.carvicim.storage.session.exceptions.UnitializedException;

//@@author yuhongherald

/**
 * Attempts to import all (@code JobEntry) into Servicing Manager
 */
public class SaveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves your reviewed job entries as an excel file.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Your reviewed job entries have been saved to %s!";

    public String getMessageSuccess(String filePath) {
        return String.format(MESSAGE_SUCCESS, filePath);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        ImportSession importSession = ImportSession.getInstance();
        if (!importSession.getSessionData().getUnreviewedJobEntries().isEmpty()) {
            throw new CommandException("Please review all remaining job entries before saving!");
        }
        try {
            return new CommandResult(getMessageSuccess(importSession.closeSession()));
        } catch (IOException e) {
            throw new CommandException("Unable to export file. Please close the application and try again.");
        } catch (UnitializedException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SaveCommand); // instanceof handles nulls
    }

}
