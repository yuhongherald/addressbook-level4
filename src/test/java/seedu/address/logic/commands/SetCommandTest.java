package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandWordException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@author yuhongherald
public class SetCommandTest {

    @Test
    public void execute_changeAdd_success() throws CommandWordException {
        Model actualModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String currentWord = AddCommand.COMMAND_WORD;
        String newWord = getUnusedCommandWord(actualModel);

        setCommandWord(expectedModel, currentWord, newWord);
        SetCommand newCommand = prepareCommand(actualModel, currentWord, newWord);
        assertCommandSuccess(newCommand, actualModel, newCommand.getMessageSuccess(), expectedModel);
    }

    @Test
    public void execute_changeSet_success() throws CommandWordException {
        Model actualModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String currentWord = SetCommand.COMMAND_WORD;
        String newWord = getUnusedCommandWord(actualModel);

        setCommandWord(expectedModel, currentWord, newWord);
        SetCommand newCommand = prepareCommand(actualModel, currentWord, newWord);
        assertCommandSuccess(newCommand, actualModel, newCommand.getMessageSuccess(), expectedModel);

        setCommandWord(expectedModel, newWord, currentWord);
        SetCommand newCommand2 = prepareCommand(actualModel, newWord, currentWord);
        assertCommandSuccess(newCommand2, actualModel, newCommand2.getMessageSuccess(), expectedModel);
    }

    @Test
    public void execute_changeCommand_failureUsed() throws CommandWordException {
        Model actualModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String currentWord = AddCommand.COMMAND_WORD;
        String newWord = SetCommand.COMMAND_WORD;

        SetCommand newCommand = prepareCommand(actualModel, currentWord, newWord);
        assertCommandFailure(newCommand, actualModel, newCommand.getMessageUsed());
    }

    @Test
    public void execute_changeCommand_failureUnused() throws CommandWordException {
        Model actualModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model testModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String currentWord = getUnusedCommandWord(actualModel);
        String newWord = getUnusedCommandWord(actualModel, currentWord);

        SetCommand newCommand = prepareCommand(actualModel, currentWord, newWord);
        assertCommandFailure(newCommand, actualModel, newCommand.getMessageUnused());
    }

    @Test
    public void execute_changeCommand_shortCircuit() throws CommandWordException {
        Model actualModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String currentWord = AddCommand.COMMAND_WORD;
        String newWord = currentWord;

        setCommandWord(expectedModel, currentWord, newWord);
        SetCommand newCommand = prepareCommand(actualModel, currentWord, newWord);
        assertCommandSuccess(newCommand, actualModel, newCommand.getMessageSuccess(), expectedModel);
    }


    private void setCommandWord(Model expectedModel, String currentWord, String newWord) throws CommandWordException {
        expectedModel.getCommandWords().setCommandWord(currentWord, newWord);
    }

    private String getUnusedCommandWord(Model actualModel) {
        String newWord = "a";
        for (int i = 0; i < actualModel.getCommandWords().commands.size(); i++) {
            if (!actualModel.getCommandWords().commands.containsValue(newWord)) {
                return newWord;
            }
            newWord += "a";
        }
        return newWord;
    }

    private String getUnusedCommandWord(Model actualModel, String otherWord) {
        if (otherWord == null || otherWord.equals("")) {
            return  getUnusedCommandWord(actualModel);
        }
        String newWord = "a";
        for (int i = 0; i < actualModel.getCommandWords().commands.size(); i++) {
            if (!actualModel.getCommandWords().commands.containsValue(newWord)
                    && !newWord.equals(otherWord)) {
                return newWord;
            }
            newWord += "a";
        }
        return newWord;
    }

    /**
     * Generates a new {@code SetCommand} which upon execution replaces (@code currentWord) with (@code newWord).
     */
    private SetCommand prepareCommand(Model model, String currentWord, String newWord) {
        SetCommand command = new SetCommand(currentWord, newWord);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
