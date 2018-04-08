package seedu.carvicim.storage.session;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.ss.usermodel.WorkbookFactory;

import seedu.carvicim.logic.commands.exceptions.CommandException;
import seedu.carvicim.model.job.Job;
import seedu.carvicim.storage.session.exceptions.DataIndexOutOfBoundsException;
import seedu.carvicim.storage.session.exceptions.FileAccessException;
import seedu.carvicim.storage.session.exceptions.FileFormatException;
import seedu.carvicim.storage.session.exceptions.UninitializedException;

//@@author yuhongherald
/**
 * A data structure used to keep track of job entries in an (@code ImportSession)
 */
public class SessionData {
    public static final String ERROR_MESSAGE_FILE_OPEN = "An excel file is already open.";
    public static final String ERROR_MESSAGE_INVALID_FILEPATH = "Please check the path to your file.";
    public static final String ERROR_MESSAGE_READ_PERMISSION = "Please enable file read permission.";
    public static final String ERROR_MESSAGE_FILE_FORMAT = "Unable to read the format of file. "
            + "Please ensure the file is in .xls or .xlsx format";
    public static final String ERROR_MESSAGE_IO_EXCEPTION = "Unable to read file. Please close the file and try again.";
    public static final String ERROR_MESSAGE_EMPTY_UNREVIWED_JOB_LIST = "There are no unreviewed job entries left!";

    public static final String FILE_PATH_CHARACTER = "/";
    public static final String SAVEFILE_SUFFIX = "-comments";
    public static final String ERROR_MESSAGE_UNINITIALIZED = "There is no imported file to save!";
    public static final String XLS_SUFFIX = ".xls";
    public static final String XLSX_SUFFIX = ".xlsx";
    public static final String TEMPFILE_NAME = "comments.temp";
    public static final String ERROR_MESSAGE_INVALID_JOB_NUMBER = "Job number not found!";

    private final ArrayList<JobEntry> jobEntries;
    private final ArrayList<JobEntry> unreviewedJobEntries;
    private final ArrayList<JobEntry> reviewedJobEntries;
    private final ArrayList<SheetWithHeaderFields> sheets;
    // implement a logger

    private File importFile;
    private Workbook workbook; // write comments to column after last row, with approval status
    private File tempFile;
    private File saveFile;


    public SessionData() {
        jobEntries = new ArrayList<>();
        unreviewedJobEntries = new ArrayList<>();
        reviewedJobEntries = new ArrayList<>();
        sheets = new ArrayList<>();
    }

    public SessionData(ArrayList<JobEntry> jobEntries, ArrayList<JobEntry> unreviewedJobEntries,
                       ArrayList<JobEntry> reviewedJobEntries,
                       ArrayList<SheetWithHeaderFields> sheets,
                       File importFile, File tempFile, File saveFile) throws FileAccessException, FileFormatException {
        this.jobEntries = jobEntries;
        this.unreviewedJobEntries = unreviewedJobEntries;
        this.reviewedJobEntries = reviewedJobEntries;
        this.sheets = sheets;
        this.importFile = importFile;
        this.tempFile = tempFile;
        this.saveFile = saveFile;
        // overwrite the old save file and load workbook data
        if (tempFile != null) {
            try {
                workbook = retrieveWorkBook(tempFile);
            } catch (IOException e) {
                throw new FileAccessException(ERROR_MESSAGE_IO_EXCEPTION);
            } catch (InvalidFormatException e) {
                throw new FileFormatException(ERROR_MESSAGE_FILE_FORMAT);
            }
        }
    }

    /**
     * Creates a copy of sessionData and returns it
     */
    public SessionData createCopy() throws CommandException {
        SessionData other = null;
        tempFile = new File(TEMPFILE_NAME);
        try {
            saveDataToFile(tempFile);
        } catch (IOException e) {
            throw new CommandException(ERROR_MESSAGE_IO_EXCEPTION);
        } catch (UninitializedException e) {
            throw new CommandException(ERROR_MESSAGE_UNINITIALIZED);
        }
        try {
            other = new SessionData(new ArrayList<>(jobEntries), new ArrayList<>(unreviewedJobEntries),
                    new ArrayList<>(reviewedJobEntries), new ArrayList<>(sheets), importFile,
                    tempFile, saveFile);
        } catch (FileAccessException | FileFormatException e) {
            throw new CommandException(e.getMessage());
        }
        return other;
    }

    public boolean isInitialized() {
        return (workbook != null);
    }

    /*===================================================================
    savefile methods
    ===================================================================*/

    /**
     * Creates a file using relative filePath of (@code importFile), then appending a SAVEFILE_SUFFIX
     */
    private File generateSaveFile() {
        requireNonNull(importFile);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(importFile.getParent());
        stringBuilder.append(FILE_PATH_CHARACTER);
        String fullFillName = importFile.getName();
        String fileName;
        String suffix = "";
        if (fullFillName.endsWith(XLS_SUFFIX)) {
            suffix = XLS_SUFFIX;
        } else if (fullFillName.endsWith(XLSX_SUFFIX)) {
            suffix = XLSX_SUFFIX;
        }
        fileName = fullFillName.substring(0, fullFillName.length() - suffix.length());

        stringBuilder.append(fileName);
        stringBuilder.append(SAVEFILE_SUFFIX);
        stringBuilder.append(suffix);
        return new File(stringBuilder.toString());
    }

    /*===================================================================
    Initialization methods
    ===================================================================*/

    /**
     * Attempts load file specified at (@code filePath) if there is no currently open file and
     * specified file exists, is readable and is an excel file
     */
    public void loadFile(String filePath) throws FileAccessException, FileFormatException {
        if (isInitialized()) {
            throw new FileAccessException(ERROR_MESSAGE_FILE_OPEN);
        }
        freeResources(); // from previous session
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileAccessException(ERROR_MESSAGE_INVALID_FILEPATH);
        } else if (!file.canRead()) {
            throw new FileAccessException(ERROR_MESSAGE_READ_PERMISSION);
        }
        importFile = file;

        try {
            workbook = retrieveWorkBook(file);
        } catch (InvalidFormatException e) {
            throw new FileFormatException(ERROR_MESSAGE_FILE_FORMAT);
        } catch (IOException e) {
            throw new FileFormatException(ERROR_MESSAGE_IO_EXCEPTION);
        }

        initializeSessionData();
    }

    /**
     * Attempts to create a (@code Workbook) for a given (@code File)
     */
    private Workbook retrieveWorkBook(File file) throws IOException, InvalidFormatException {
        saveFile = generateSaveFile();
        Workbook workbook;
        if (saveFile.exists()) {
            workbook = WorkbookFactory.create(saveFile);
            return workbook;
        } else {
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            workbook = WorkbookFactory.create(file);
            workbook.write(fileOutputStream);
            workbook.close();
            workbook = WorkbookFactory.create(saveFile);
            return workbook;
        }
    }
    /**
     * Attempts to parse the column headers and retrieve job entries
     */
    public void initializeSessionData() throws FileFormatException {
        SheetWithHeaderFields sheetWithHeaderFields;
        SheetParser sheetParser;
        Sheet sheet;

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            sheet = workbook.getSheetAt(workbook.getFirstVisibleTab() + i);
            sheetParser = new SheetParser(sheet);
            sheetWithHeaderFields = sheetParser.parseSheetWithHeaderField();
            addSheet(sheetWithHeaderFields);
        }
    }

    public String saveDataToSaveFile() throws IOException, UninitializedException {
        return saveDataToFile(saveFile);
    }

    /**
     * Saves feedback to (@code file)
     */
    public String saveDataToFile(File file) throws IOException, UninitializedException {
        if (!isInitialized()) {
            throw new UninitializedException(ERROR_MESSAGE_UNINITIALIZED);
        }
        for (JobEntry jobEntry : jobEntries) {
            SheetWithHeaderFields sheet = sheets.get(jobEntry.getSheetNumber());
            sheet.commentJobEntry(jobEntry.getRowNumber(), jobEntry.getCommentsAsString());
            if (jobEntry.isApproved()) {
                sheet.approveJobEntry(jobEntry.getRowNumber());
            } else {
                sheet.rejectJobEntry(jobEntry.getRowNumber());
            }
        }

        if (file == null) { // does not check if a file exists
            file = generateSaveFile();
        }
        FileOutputStream fileOut = new FileOutputStream(file);
        String path = file.getAbsolutePath();
        workbook.write(fileOut);
        fileOut.close();
        return path;
    }
    /**
     * Releases resources associated with ImportSession by nulling field
     */
    public void freeResources() {
        if (workbook != null) {
            try {
                workbook.close();
            } catch (IOException e) {
                ; // can't close it, but application is already closing
            }
        }
        workbook = null;
        importFile = null;
        if (tempFile != null) {
            tempFile.delete();
        }
        tempFile = null;
        saveFile = null;
        unreviewedJobEntries.clear();
        reviewedJobEntries.clear();
        sheets.clear();
    }

    /**
     * Adds job entries from (@code sheetWithHeaderFields) into (@code SessionData)
     */
    public void addSheet(SheetWithHeaderFields sheetWithHeaderFields) {
        Iterator<JobEntry> jobEntryIterator = sheetWithHeaderFields.iterator();
        JobEntry jobEntry;
        while (jobEntryIterator.hasNext()) {
            jobEntry = jobEntryIterator.next();
            jobEntries.add(jobEntry);
            unreviewedJobEntries.add(jobEntry);
        }
        sheets.add(sheetWithHeaderFields.getSheetIndex(), sheetWithHeaderFields);
    }

    /*===================================================================
    Job review methods
    ===================================================================*/
    /**
     * @return a copy of unreviewed jobs stored in this sheet
     */
    public List<Job> getUnreviewedJobEntries() {
        return Collections.unmodifiableList(unreviewedJobEntries);
    }

    /**
     * @return a copy of reviewed jobs stored in this sheet
     */
    public List<Job> getReviewedJobEntries() {
        return Collections.unmodifiableList(reviewedJobEntries);
    }

    /**
     * Reviews all remaining jobs using (@code reviewJobEntry). Writes to (@code saveFile) when done.
     */
    public void reviewAllRemainingJobEntries(boolean approved, String comments) throws CommandException {
        try {
            while (!getUnreviewedJobEntries().isEmpty()) {
                    reviewJobEntry(0, approved, comments);
            }
        } catch (DataIndexOutOfBoundsException e) {
            throw new CommandException(e.getMessage());
        }

        try {
            saveDataToSaveFile();
        } catch (UninitializedException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new CommandException(ERROR_MESSAGE_IO_EXCEPTION);
        }
    }

    /**
     * Reviews a (@code JobEntry) specified by (@code listIndex). Writes to (@code saveFile) when done.
     * @param jobNumber index of (@code JobEntry) in (@code unreviewedJobEntries)
     * @param approved whether job entry will be added to Carvicim
     * @param comments feedback in string representation
     */
    public void reviewJobEntryUsingJobNumber(int jobNumber, boolean approved, String comments)
            throws CommandException {
        if (unreviewedJobEntries.isEmpty()) {
            throw new IllegalStateException(ERROR_MESSAGE_EMPTY_UNREVIWED_JOB_LIST);
        }
        JobEntry entry;
        for (int i = 0; i < unreviewedJobEntries.size(); i++) {
            entry = unreviewedJobEntries.get(i);
            if (entry.getJobNumber().asInteger() == jobNumber) {
                try {
                    reviewJobEntry(i, approved, comments);
                } catch (DataIndexOutOfBoundsException e) {
                    throw new CommandException(e.getMessage());
                }
                try {
                    saveDataToSaveFile();
                } catch (IOException e) {
                    throw new CommandException(ERROR_MESSAGE_IO_EXCEPTION);
                } catch (UninitializedException e) {
                    throw new CommandException(ERROR_MESSAGE_UNINITIALIZED);
                }
                return;
            }
        }
        throw new CommandException(ERROR_MESSAGE_INVALID_JOB_NUMBER);
    }

    /**
     * Reviews a (@code JobEntry) specified by (@code listIndex)
     * @param listIndex index of (@code JobEntry) in (@code unreviewedJobEntries)
     * @param approved whether job entry will be added to Carvicim
     * @param comments feedback in string representation
     */
    private void reviewJobEntry(int listIndex, boolean approved, String comments) throws DataIndexOutOfBoundsException {
        if (unreviewedJobEntries.isEmpty()) {
            throw new IllegalStateException(ERROR_MESSAGE_EMPTY_UNREVIWED_JOB_LIST);
        } else if (listIndex < 0 || listIndex >= unreviewedJobEntries.size()) {
            throw new DataIndexOutOfBoundsException("Rows", 0, unreviewedJobEntries.size(), listIndex);
        }

        JobEntry jobEntry = unreviewedJobEntries.get(listIndex);
        jobEntry.review(approved, comments);
        unreviewedJobEntries.remove(jobEntry);
        if (approved) {
            reviewedJobEntries.add(jobEntry);
        }
    }
}
