package seedu.address.model.session;

import java.util.ArrayList;
import java.util.Iterator;

import seedu.address.model.session.exceptions.DataIndexOutOfBoundsException;

//@@author yuhongherald
/**
 * A data structure used to keep track of job entries in an (@code ImportSession)
 */
public class SessionData {
    public static final String ERROR_MESSAGE_EMPTY_UNREVIWED_JOB_LIST = "There are no unreviewed job entries left!";

    private final ArrayList<JobEntry> unreviewedJobEntries;
    private final ArrayList<JobEntry> reviewedJobEntries;
    private final ArrayList<SheetWithHeaderFields> headerFields;

    // will be using an ObservableList

    SessionData() {
        unreviewedJobEntries = new ArrayList<>();
        reviewedJobEntries = new ArrayList<>();
        headerFields = new ArrayList<>();
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

    /**
     * Adds job entries from (@code sheetWithHeaderFields) into (@code SessionData)
     */
    public void addUnreviewedJobEntries(SheetWithHeaderFields sheetHeaderFields) {
        Iterator<JobEntry> jobEntryIterator = sheetHeaderFields.iterator();
        while (jobEntryIterator.hasNext()) {
            unreviewedJobEntries.add(jobEntryIterator.next());
        }
    }

    /**
     * Reviews a (@code JobEntry) specified by (@code listIndex)
     * @param listIndex index of (@code JobEntry) in (@code unreviewedJobEntries)
     * @param approved whether job entry will be added to CarviciM
     * @param comments feedback in string representation
     */
    public void reviewJobEntry(int listIndex, boolean approved, String comments) throws DataIndexOutOfBoundsException {
        if (unreviewedJobEntries.isEmpty()) {
            throw new IllegalStateException(ERROR_MESSAGE_EMPTY_UNREVIWED_JOB_LIST);
        } else if (listIndex < 0 || listIndex >= unreviewedJobEntries.size()) {
            throw new DataIndexOutOfBoundsException("Rows", 0, unreviewedJobEntries.size(), listIndex);
        }

        JobEntry jobEntry = unreviewedJobEntries.get(listIndex);
        jobEntry.review(approved, comments);
        unreviewedJobEntries.remove(jobEntry);
        reviewedJobEntries.add(jobEntry);

    }
}
