package seedu.servicing.logic.commands;

import static seedu.servicing.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.servicing.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.servicing.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.servicing.logic.CommandHistory;
import seedu.servicing.logic.UndoRedoStack;
import seedu.servicing.model.Model;
import seedu.servicing.model.ModelManager;
import seedu.servicing.model.UserPrefs;
import seedu.servicing.model.person.Person;
import seedu.servicing.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() throws Exception {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(prepareCommand(validPerson, model), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(prepareCommand(personInList, model), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code person} into the {@code model}.
     */
    private AddCommand prepareCommand(Person person, Model model) {
        AddCommand command = new AddCommand(person);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
