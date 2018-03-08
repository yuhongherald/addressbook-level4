package seedu.address.model.job;

//@author owzhenwei
/**
 * Represent a job number in the servicing manager
 */
public class JobNumber {
    private static int NEXT_JOB_NUMBER;

    public final String jobNumber;

    public JobNumber() {
        jobNumber = Integer.toString(NEXT_JOB_NUMBER);
        incrementNextJobNumber();
    }

    /**
     * Initialize the next job number of the car servicing manager
     */
    public static void initNextJobNumber(String args) {
        NEXT_JOB_NUMBER = Integer.parseInt(args);
    }

    public static void incrementNextJobNumber() {
        NEXT_JOB_NUMBER++;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobNumber // instanceof handles nulls
                && this.jobNumber.equals(((JobNumber) other).jobNumber)); // state check
    }

    @Override
    public int hashCode() {
        return jobNumber.hashCode();
    }
}
