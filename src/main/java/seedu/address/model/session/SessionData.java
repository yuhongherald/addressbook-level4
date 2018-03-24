package seedu.address.model.session;

import java.util.ArrayList;

/**
 * A data structure used to keep track of job entries in an (@code ImportSession)
 */
public class SessionData {
    public static final String COMMENT_FORMAT = "[A-Za-z0-9]+";
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
            // @TODO throw exception
        } else if (listIndex < 0 || listIndex >= unreviewedJobEntries.size()) {
            // @TODO throw exception
        }

        if (!comments.matches(COMMENT_FORMAT)) {
            // @TODO throw exception comment must be alphanumeric
        }

        JobEntry jobEntry = unreviewedJobEntries.get(listIndex);
        jobEntry.review(approved, comments);
        unreviewedJobEntries.remove(jobEntry);
        reviewedJobEntries.add(jobEntry);

    }
}
