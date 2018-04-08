package seedu.carvicim.logic.commands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import seedu.carvicim.logic.commands.exceptions.CommandException;
import seedu.carvicim.model.job.Job;
import seedu.carvicim.storage.session.ImportSession;
import seedu.carvicim.storage.session.SessionData;

//@@author yuhongherald

/**
 * Accepts all remaining unreviewed job entries into Servicing Manager
 */
public class AcceptAllCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "acceptAll";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Accepts all unreviewed job entries. "
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "%d job entries accepted!";

    public String getMessageSuccess(int entries) {
        return String.format(MESSAGE_SUCCESS, entries);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        SessionData sessionData = ImportSession.getInstance().getSessionData();
        if (sessionData.getUnreviewedJobEntries().isEmpty()) {
            throw new CommandException("There are no job entries to review!");
        }
        List<Job> jobs = new ArrayList<>(sessionData
                .reviewAllRemainingJobEntries(true, ""));
        model.addJobs(jobs);
        return new CommandResult(getMessageSuccess(jobs.size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AcceptAllCommand); // instanceof handles nulls
    }

}
