package seedu.address.model.session;

import java.util.ArrayList;

/**
 * A data structure used to keep track of job entries in an (@code ImportSession)
 */
public class SessionData {
    public static final String COMMENT_FORMAT = "[A-Za-z0-9]+";
    public static final String ERROR_MESSAGE_OUT_OF_BOUNDS = "Expected index 0 to %d, but got %d";
    public static final String ERROR_MESSAGE_EMPTY_UNREVIWED_JOB_LIST = "There are no unreviewed job entries left!";
    private final ArrayList<JobEntry> unreviewedJobEntries;
    private final ArrayList<JobEntry> reviewedJobEntries;
    // will be using an ObservableList

    SessionData() {
        unreviewedJobEntries = new ArrayList<>();
        reviewedJobEntries = new ArrayList<>();
    }

    /**
     * @return a copy of unreviewed job entries stored in this sheet
     */
    public ArrayList<JobEntry> getUnreviewedJobEntries() {
        return new ArrayList<>(unreviewedJobEntries);
    }

    /**
     * @return a copy of reviewed job entries stored in this sheet
     */
    public ArrayList<JobEntry> getReviewedJobEntries() {
        return new ArrayList<>(reviewedJobEntries);
    }

    public void addUnreviewedJobEntry(JobEntry jobEntry) {
        unreviewedJobEntries.add(jobEntry);
    }

    /**
     * Reviews a (@code JobEntry) specified by (@code listIndex)
     * @param listIndex index of
     * @param approved
     * @param comments
     */
    public void reviewJobEntry(int listIndex, boolean approved, String comments) {
        if (unreviewedJobEntries.isEmpty()) {
            throw new IllegalStateException(ERROR_MESSAGE_EMPTY_UNREVIWED_JOB_LIST);
        } else if (listIndex < 0 || listIndex >= unreviewedJobEntries.size()) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format(ERROR_MESSAGE_OUT_OF_BOUNDS, unreviewedJobEntries.size(), listIndex));
        }

        if (!comments.matches(COMMENT_FORMAT)) {
            //throw new ArgumentE
        }

        JobEntry jobEntry = unreviewedJobEntries.get(listIndex);
        jobEntry.review(approved, comments);
        unreviewedJobEntries.remove(jobEntry);
        reviewedJobEntries.add(jobEntry);

    }
}
