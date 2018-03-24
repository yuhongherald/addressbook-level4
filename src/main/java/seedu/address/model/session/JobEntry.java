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
 *
 */
public class JobEntry extends Job {
    private boolean reviewed;
    private boolean approved;
    private String comments;

    public JobEntry(Person client, VehicleNumber vehicleNumber, JobNumber jobNumber, Date date,
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

    public void writeComments(String comments) {

    }

    public String getComments() {
        return comments;
    }
}
