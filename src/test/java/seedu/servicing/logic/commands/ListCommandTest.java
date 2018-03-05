package seedu.servicing.logic.commands;

import static seedu.servicing.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.servicing.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.servicing.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.servicing.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.servicing.logic.CommandHistory;
import seedu.servicing.logic.UndoRedoStack;
import seedu.servicing.model.Model;
import seedu.servicing.model.ModelManager;
import seedu.servicing.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCommand listCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listCommand = new ListCommand();
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
