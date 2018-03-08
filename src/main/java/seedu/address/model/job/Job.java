package seedu.address.model.job;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.Person;

//@author owzhenwei
/**
 * Represents a Job in the car servicing manager
 */
public class Job {
    private final Person customer;
    private final VehicleID vehicleID;
    private final JobNumber jobNumber;
    
    public Job(Person customer, VehicleID vehicleID, JobNumber jobNumber) {
        requireAllNonNull(customer, vehicleID);
        this.customer = customer;
        this.vehicleID = vehicleID;
        this.jobNumber = jobNumber;
    }

    public JobNumber getJobNumber() {
        return jobNumber;
    }

    public VehicleID getVehicleID() {
        return vehicleID;
    }

    public Person getCustomer() {
        return customer;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Job)) {
            return false;
        }

        Job otherJob = (Job) other;
        return otherJob.getCustomer().equals(this.getCustomer())
                && otherJob.getVehicleID().equals(this.getVehicleID())
                && otherJob.getJobNumber() == this.getJobNumber();

    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, vehicleID, jobNumber);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Job Number: ")
                .append(getJobNumber())
                .append(" Vehicle ID: ")
                .append(getVehicleID())
                .append( " Name: ")
                .append(customer.getName())
                .append(" Email: ")
                .append(customer.getEmail());
        return builder.toString();
    }
}
