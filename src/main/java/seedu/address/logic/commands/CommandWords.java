package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import seedu.address.logic.commands.exceptions.CommandWordException;

/**
 * A data structure used to contain the mappings of a command to a word
 */
public class CommandWords {
    public static final String MESSAGE_INACTIVE = "%s is not an active command.";
    public final HashMap<String, String> commands;
    /**
     * Creates a data structure to maintain used command words.
     */
    public CommandWords() {
        commands = new HashMap<>();
        commands.put(AddCommand.COMMAND_WORD, AddCommand.COMMAND_WORD);
        commands.put(ClearCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD);
        commands.put(DeleteCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD);
        commands.put(EditCommand.COMMAND_WORD, EditCommand.COMMAND_WORD);
        commands.put(ExitCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD);
        commands.put(FindCommand.COMMAND_WORD, FindCommand.COMMAND_WORD);
        commands.put(HelpCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD);
        commands.put(HistoryCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD);
        commands.put(ListCommand.COMMAND_WORD, ListCommand.COMMAND_WORD);
        commands.put(RedoCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD);
        commands.put(SelectCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD);
        commands.put(SetCommand.COMMAND_WORD, SetCommand.COMMAND_WORD);
        commands.put(UndoCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD);
    }

    public CommandWords(CommandWords commandWords) {
        requireNonNull(commandWords);
        commands = new HashMap<>();
        commands.putAll(commandWords.commands);
    }

    /**
     * Retrieves a command word using a key
     * @param key
     * @return command
     * @throws CommandWordException
     */
    public String getCommandWord(String key) throws CommandWordException {
        String commandWord = commands.get(key);
        if (commandWord == null) {
            throw new CommandWordException(toStringWithMessage(String.format(MESSAGE_INACTIVE, key)));
        }
        return commandWord;
    }

    /**
     * Retrieves a command key using word
     * @param value
     * @return command
     * @throws CommandWordException
     */
    public String getCommandKey(String value) throws CommandWordException {
        Iterator<Map.Entry<String, String>> commandList = commands.entrySet().iterator();
        Map.Entry<String, String> currentCommand;
        while (commandList.hasNext()) {
            currentCommand = commandList.next();
            if (currentCommand.getValue().equals(value)) {
                return currentCommand.getKey();
            }
        }
        throw new CommandWordException(toStringWithMessage(String.format(MESSAGE_INACTIVE, value)));
    }

    /**
     * Sets currentWord to newWord
     * @param currentWord Active command word to be replaced
     * @param newWord Command word to be replaced with
     * @throws CommandWordException currentWord is not valid
     */
    public void setCommandWord(String currentWord, String newWord) throws CommandWordException {
        Iterator<Map.Entry<String, String>> commandList = commands.entrySet().iterator();
        Map.Entry<String, String> currentCommand;
        while (commandList.hasNext()) {
            currentCommand = commandList.next();
            if (currentCommand.getValue().equals(currentWord)) {
                commands.remove(currentCommand.getKey());
                commands.put(currentCommand.getKey(), newWord);
                return;
            }
        }
        StringBuilder builder = new StringBuilder();
        throw new CommandWordException(toStringWithMessage(String.format(MESSAGE_INACTIVE, currentWord)));
    }

    /**
     * Resets the existing data of this {@code CommandWords} with {@code newCommandWords}.
     */
    public void resetData(CommandWords newCommandWords) {
        requireNonNull(newCommandWords);
        commands.clear();
        commands.putAll(newCommandWords.commands);
    }

    /**
     * String that shows command words, followed by a message
     * @param message to be appended
     * @return String to be displayed
     */
    private String toStringWithMessage(String message) {
        return message;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Commands: \n");
        Iterator<Map.Entry<String, String>> commandList = commands.entrySet().iterator();
        Map.Entry<String, String> currentCommand;
        while (commandList.hasNext()) {
            currentCommand = commandList.next();
            builder.append(currentCommand.getKey() + ":" + currentCommand.getValue() + "\n");
        }
        return builder.toString();
    }

}
