package seedu.carvicim.logic.parser;

import seedu.carvicim.commons.exceptions.IllegalValueException;
import seedu.carvicim.logic.commands.RejectAllCommand;
import seedu.carvicim.logic.commands.RejectCommand;
import seedu.carvicim.logic.parser.exceptions.ParseException;

import static seedu.carvicim.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.carvicim.logic.parser.ParserUtil.parseInteger;

//@@author yuhongherald

/**
 * Parses input arguments and creates a new RejectAllCommand object
 */
public class RejectAllCommandParser implements Parser<RejectAllCommand> {

    /**
     * Parses the given {@code String} of arg
     * uments in the context of the RejectAllCommand
     * and returns a RejectAllCommand object for execution.
     */
    public RejectAllCommand parse(String args) throws ParseException {
        String comment = args.trim();
        return new RejectAllCommand(comment);
    }

}
