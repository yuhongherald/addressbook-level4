package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.CommandWordException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Sets a command word to user preferred command word
 */
public class SetCommand extends Command {

    public static final String COMMAND_WORD = "set";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a command word to user preference. "
            + "Parameters: CURRENT_COMMAND_WORD NEW_COMMAND_WORD"
            + "Example: " + COMMAND_WORD + " "
            + AddCommand.COMMAND_WORD + "NEW_COMMAND";

    public static final String MESSAGE_SUCCESS = "%s has been replaced with %s!";
    public static final String MESSAGE_ERROR = "%s is not a valid command. Type 'help' to see list of commands.";

    private final String currentWord;
    private final String newWord;

    /**
     * Creates an SetCommand to set the specified {@code CommandWords}
     */
    public SetCommand(String currentWord, String newWord) {
        this.currentWord = currentWord;
        this.newWord = newWord;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.getCommandWords().setCommandWord(currentWord, newWord);
        } catch (CommandWordException e) {
            throw new CommandException(String.format(MESSAGE_ERROR, currentWord));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, currentWord, newWord));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetCommand // instanceof handles nulls
                && currentWord.equals(((SetCommand) other).currentWord)
                && newWord.equals(((SetCommand) other).newWord));
    }
}
