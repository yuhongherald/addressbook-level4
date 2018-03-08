package seedu.address.model.job;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.Person;

/**
 * Represents a Job in the car servicing manager
 */
public class Job {
    private final Person customer;
    private final VehicleID vehicleID;
    private final int jobNumber;

    private static int nextJobNumber;

    public Job(Person customer, VehicleID vehicleID) {
        requireAllNonNull(customer, vehicleID);
        this.customer = customer;
        this.vehicleID = vehicleID;
        this.jobNumber = nextJobNumber;
        incrementNextJobNumber();
    }

    /**
     * Initialize the next job number of the car servicing manager
     */
    public static void initNextJobNumber(int num) {
        nextJobNumber = num;
    }

    public static void incrementNextJobNumber() {
        nextJobNumber++;
    }

    public int getJobNumber() {
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
