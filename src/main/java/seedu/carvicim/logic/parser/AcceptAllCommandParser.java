package seedu.carvicim.logic.parser;

import seedu.carvicim.commons.exceptions.IllegalValueException;
import seedu.carvicim.logic.commands.AcceptAllCommand;
import seedu.carvicim.logic.commands.AcceptCommand;
import seedu.carvicim.logic.parser.exceptions.ParseException;

import static seedu.carvicim.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.carvicim.logic.parser.ParserUtil.parseInteger;

//@@author yuhongherald

/**
 * Parses input arguments and creates a new AcceptAllCommand object
 */
public class AcceptAllCommandParser implements Parser<AcceptAllCommand> {

    /**
     * Parses the given {@code String} of arg
     * uments in the context of the AcceptAllCommand
     * and returns an AcceptAllCommand object for execution.
     */
    public AcceptAllCommand parse(String args) {
        String comment = args.trim();
        return new AcceptAllCommand(comment);
    }

}
