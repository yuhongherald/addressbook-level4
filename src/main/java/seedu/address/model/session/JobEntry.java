package seedu.address.model.session;

import seedu.address.model.job.Date;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobNumber;
import seedu.address.model.job.Status;
import seedu.address.model.job.VehicleNumber;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniqueEmployeeList;
import seedu.address.model.remark.RemarkList;

//@@author yuhongherald
/**
 * Represents a job entry in an (@link ImportSession)
 */
public class JobEntry extends Job {

    private boolean reviewed;
    private boolean approved;
    private String comments;

    JobEntry(Person client, VehicleNumber vehicleNumber, JobNumber jobNumber, Date date,
                    UniqueEmployeeList assignedEmployees, Status status, RemarkList remarks) {
        super(client, vehicleNumber, jobNumber, date, assignedEmployees, status, remarks);
        reviewed = false;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public boolean isApproved() {
        return approved;
    }

    /**
     * Marks (@code JobEntry) as reviewed.
     * @param approved whether (@code JobEntry) is going to be added to CarviciM
     * @param comments feedback for (@code JobEntry) in String representation
     */
    public void review(boolean approved, String comments) {
        this.approved = approved;
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }
}
