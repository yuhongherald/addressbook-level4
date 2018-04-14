package seedu.carvicim.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static seedu.carvicim.testutil.TypicalEmployees.getTypicalCarvicimWithAssignedJobs;

import org.junit.Test;

import seedu.carvicim.logic.CommandHistory;
import seedu.carvicim.logic.UndoRedoStack;
import seedu.carvicim.model.Model;
import seedu.carvicim.model.ModelManager;
import seedu.carvicim.model.UserPrefs;
import seedu.carvicim.storage.ImportSessionTest;
import seedu.carvicim.storage.session.ImportSession;

//@@author yuhongherald
public class AcceptCommandTest extends ImportSessionTest {

    private Model model = new ModelManager(getTypicalCarvicimWithAssignedJobs(), new UserPrefs());

    @Test
    public void equals() {
        String comment = "comment";
        AcceptCommand acceptCommand1 = prepareCommand(1, comment);
        AcceptCommand remarkCommand1Copy = prepareCommand(1, comment);
        AcceptCommand acceptCommand2 = prepareCommand(2, comment);
        AcceptCommand acceptCommand3 = prepareCommand(1, "");

        // same object -> returns true
        assertTrue(acceptCommand1.equals(acceptCommand1));

        // same values -> returns true
        assertTrue(acceptCommand1.equals(remarkCommand1Copy));

        // different types -> returns false
        assertFalse(acceptCommand1.equals(1));

        // different job index -> return false
        assertFalse(acceptCommand1.equals(acceptCommand2));

        // different comments -> return false
        assertFalse(acceptCommand1.equals(acceptCommand3));

        // null -> return false
        assertFalse(acceptCommand1.equals(null));
    }

    @Test
    public void execute_acceptWithoutComment_success() throws Exception {
        setup(ERROR_INPUT_FILE, ERROR_RESULT_FILE, ERROR_OUTPUT_FILE);
        ImportSession.getInstance().initializeSession(inputPath);
        AcceptCommand command = prepareCommand(1, "");
        command.execute();
        // check model, check -comments file
        cleanup();
    }

    @Test
    public void execute_acceptWithComment_success() {
    }

    @Test
    public void execute_acceptOutOfBounds_failure() {
    }

    @Test
    public void execute_acceptWithoutImport_failure() {
    }

    private AcceptCommand prepareCommand(int jobIndex, String comments) {
        AcceptCommand acceptCommand = new AcceptCommand(jobIndex, comments);
        acceptCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return acceptCommand;
    }
}
