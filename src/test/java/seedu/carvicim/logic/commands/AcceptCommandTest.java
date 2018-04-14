package seedu.carvicim.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;

import seedu.carvicim.logic.CommandHistory;
import seedu.carvicim.logic.UndoRedoStack;
import seedu.carvicim.model.Model;
import seedu.carvicim.model.ModelManager;
import seedu.carvicim.model.job.Date;
import seedu.carvicim.model.job.Job;
import seedu.carvicim.model.job.JobNumber;
import seedu.carvicim.model.job.Status;
import seedu.carvicim.model.job.VehicleNumber;
import seedu.carvicim.model.person.Email;
import seedu.carvicim.model.person.Employee;
import seedu.carvicim.model.person.Name;
import seedu.carvicim.model.person.Person;
import seedu.carvicim.model.person.Phone;
import seedu.carvicim.model.person.UniqueEmployeeList;
import seedu.carvicim.model.person.exceptions.DuplicateEmployeeException;
import seedu.carvicim.model.remark.Remark;
import seedu.carvicim.model.remark.RemarkList;
import seedu.carvicim.storage.ImportSessionTestEnv;
import seedu.carvicim.storage.session.ImportSession;

//@@author yuhongherald
public class AcceptCommandTest extends ImportSessionTestEnv {
    private Remark comment;

    private Model expectedModelWithoutComment;
    private Model expectedModelWithComment;

    @Before
    public void setup() throws DuplicateEmployeeException {
        Employee jim = new Employee(new Name("Jim"), new Phone("87654321"), new Email("jim@gmail.com"),
                Collections.emptySet());
        Person client = new Person(new Name("JD"), new Phone("91234567"), new Email("jd@gmail.com"));
        UniqueEmployeeList uniqueEmployeeList = new UniqueEmployeeList();
        uniqueEmployeeList.add(jim);
        Job job = new Job(client, new VehicleNumber("SXX1234X"), new JobNumber("1"), new Date(), uniqueEmployeeList,
                new Status(Status.STATUS_ONGOING), new RemarkList());
        comment = new Remark("good job!");
        RemarkList remarkList = new RemarkList();
        remarkList.add(comment);
        Job jobWithComment = new Job(client, new VehicleNumber("SXX1234X"), new JobNumber("1"), new Date(),
                uniqueEmployeeList, new Status(Status.STATUS_ONGOING), remarkList);
        expectedModelWithoutComment = new ModelIgnoreJobDates(jim, job);
        expectedModelWithComment = new ModelIgnoreJobDates(jim, jobWithComment);
    }

    @Test
    public void equals() {
        String comment = "comment";
        AcceptCommand acceptCommand1 = prepareCommand(1, comment);
        AcceptCommand remarkCommand1Copy = prepareCommand(1, comment);
        AcceptCommand acceptCommand2 = prepareCommand(2, comment);
        AcceptCommand acceptCommand3 = prepareCommand(1, "");

        // same object -> returns true
        assertTrue(acceptCommand1.equals(acceptCommand1));

        // same values -> returns true
        assertTrue(acceptCommand1.equals(remarkCommand1Copy));

        // different types -> returns false
        assertFalse(acceptCommand1.equals(1));

        // different job index -> return false
        assertFalse(acceptCommand1.equals(acceptCommand2));

        // different comments -> return false
        assertFalse(acceptCommand1.equals(acceptCommand3));

        // null -> return false
        assertFalse(acceptCommand1.equals(null));
    }

    @Test
    public void execute_acceptWithoutComment_success() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        setup(ERROR_INPUT_FILE, ERROR_RESULT_FILE, ERROR_OUTPUT_FILE);
        ImportSession.getInstance().initializeSession(inputPath);
        outputPath = classLoader.getResource(expectedOutputPath).getPath();
        outputFilePath = outputPath;
        AcceptCommand command = prepareCommand(1, "");
        command.execute();
        // assertEquals
        cleanup();
    }

    @Test
    public void execute_acceptWithComment_success() {
    }

    @Test
    public void execute_acceptOutOfBounds_failure() {
    }

    @Test
    public void execute_acceptWithoutImport_failure() {
    }

    private AcceptCommand prepareCommand(int jobIndex, String comments) {
        AcceptCommand acceptCommand = new AcceptCommand(jobIndex, comments);
        acceptCommand.setData(new ModelManager(), new CommandHistory(), new UndoRedoStack());
        return acceptCommand;
    }

    private class ModelIgnoreJobDates extends ModelManager {
        public ModelIgnoreJobDates(Employee employee, Job job) throws DuplicateEmployeeException {
            super();
            addPerson(employee);
            addJob(job);
        }

        @Override
        public boolean equals(Object obj) {
            // short circuit if same object
            if (obj == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(obj instanceof ModelManager)) {
                return false;
            }

            // state check
            ModelManager other = (ModelManager) obj;
            ObservableList<Job> jobList = getFilteredJobList();
            for (Job job : getFilteredJobList()) {
                ;
            }
            return getCarvicim().equals(other.getCarvicim())
                    && getFilteredPersonList().equals(other.getFilteredPersonList())
                    && getCommandWords().equals(other.getCommandWords());

        }
    }
}
