package seedu.servicing.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.servicing.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.servicing.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.servicing.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.servicing.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.servicing.logic.commands.exceptions.CommandException;
import seedu.servicing.model.Model;
import seedu.servicing.model.ModelManager;
import seedu.servicing.model.UserPrefs;
import seedu.servicing.model.person.Person;
import seedu.servicing.model.person.exceptions.PersonNotFoundException;

public class UndoableCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final DummyCommand dummyCommand = new DummyCommand(model);

    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeUndo() throws Exception {
        dummyCommand.execute();
        deleteFirstPerson(expectedModel);
        assertEquals(expectedModel, model);

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // undo() should cause the model's filtered list to show all persons
        dummyCommand.undo();
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertEquals(expectedModel, model);
    }

    @Test
    public void redo() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // redo() should cause the model's filtered list to show all persons
        dummyCommand.redo();
        deleteFirstPerson(expectedModel);
        assertEquals(expectedModel, model);
    }

    /**
     * Deletes the first person in the model's filtered list.
     */
    class DummyCommand extends UndoableCommand {
        DummyCommand(Model model) {
            this.model = model;
        }

        @Override
        public CommandResult executeUndoableCommand() throws CommandException {
            Person personToDelete = model.getFilteredPersonList().get(0);
            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                fail("Impossible: personToDelete was retrieved from model.");
            }
            return new CommandResult("");
        }
    }
}
