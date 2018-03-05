package seedu.servicing.logic.commands;

import static seedu.servicing.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.servicing.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.servicing.logic.CommandHistory;
import seedu.servicing.logic.UndoRedoStack;
import seedu.servicing.model.Model;
import seedu.servicing.model.ModelManager;
import seedu.servicing.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, ClearCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, ClearCommand.MESSAGE_SUCCESS, model);
    }

    /**
     * Generates a new {@code ClearCommand} which upon execution, clears the contents in {@code model}.
     */
    private ClearCommand prepareCommand(Model model) {
        ClearCommand command = new ClearCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
