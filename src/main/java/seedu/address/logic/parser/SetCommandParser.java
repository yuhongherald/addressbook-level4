package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.SetCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.util.Set;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class SetCommandParser implements Parser<SetCommand> {
    public static final String MESSAGE_COMMANDS_INVALID_NUMBER = "%s command requires 2 command words.";
    /**
     * Parses the given {@code String} of arg
     * uments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetCommand parse(String args) throws ParseException {
        String[] commandWords = args.split("\\s+");
        if (commandWords.length != 3) {
            IllegalValueException ive = new IllegalValueException(
                    String.format(SetCommand.MESSAGE_USAGE, commandWords[0]));
            throw new ParseException(ive.getMessage(), ive);
        }
        return new SetCommand(commandWords[1].trim(), commandWords[2].trim());
    }

}
