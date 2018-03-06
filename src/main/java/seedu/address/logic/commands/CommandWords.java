package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.CommandWordException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CommandWords {
    public static final String MESSAGE_INACTIVE = "%s is not an active command.";
    private HashMap<String, String> commands;
    /**
     * Creates a data structure to maintain used command words.
     */
    public CommandWords() {
        commands = new HashMap<>();
        commands.put(AddCommand.COMMAND_WORD, AddCommand.COMMAND_WORD);
        commands.put(ClearCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD);
        commands.put(DeleteCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD);


        //...
    }

    public String getCommandWord(String key) throws CommandWordException {
        String commandWord = commands.get(key);
        if (commandWord == null) {
            throw new CommandWordException(String.format(MESSAGE_INACTIVE, key));
        }
        return commandWord;
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
        throw new CommandWordException(String.format(MESSAGE_INACTIVE, currentWord));
    }
}
