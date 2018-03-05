package seedu.servicing.logic.commands;

import static seedu.servicing.logic.UndoRedoStackUtil.prepareStack;
import static seedu.servicing.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.servicing.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.servicing.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.servicing.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.servicing.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.servicing.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.servicing.logic.CommandHistory;
import seedu.servicing.logic.UndoRedoStack;
import seedu.servicing.model.Model;
import seedu.servicing.model.ModelManager;
import seedu.servicing.model.UserPrefs;

public class RedoCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final DeleteCommand deleteCommandOne = new DeleteCommand(INDEX_FIRST_PERSON);
    private final DeleteCommand deleteCommandTwo = new DeleteCommand(INDEX_SECOND_PERSON);

    @Before
    public void setUp() throws Exception {
        deleteCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandOne.preprocessUndoableCommand();
        deleteCommandTwo.preprocessUndoableCommand();
    }

    @Test
    public void execute() {
        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(deleteCommandTwo, deleteCommandOne));
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // multiple commands in redoStack
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
}
