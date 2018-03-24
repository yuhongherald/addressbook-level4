package seedu.address.model.session;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.HashMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import seedu.address.model.session.exceptions.FileAccessException;
import seedu.address.model.session.exceptions.FileFormatException;

//@@author yuhongherald
/**
 * Used to store data relevant to importing of (@code Job) from (@code inFile) and
 * exporting (@code Job) with commens to (@code outFile)
 */
public class ImportSession {
    public static final String[] JOB_ENTRY_COMPULSORY_FIELDS = { // ignore case when reading headings
        "Client name", "Client phone", "Client email", "Vehicle number", "Assigned Employees"
    };
    public static final String[] JOB_ENTRY_OPTIONAL_FIELDS = { // ignore case when reading headings
        "Job number", "Date", "Status", "Remarks"
    };
    private static ImportSession session;

    private boolean initialized;
    private File inFile;
    private Workbook inWorkbook;
    private Workbook outWorkbook;
    private SessionData sessionData;
    private File outFile;
    private HashMap<String, RowData> rowData;

    private ImportSession() {
        initialized = false;
    }

    public static ImportSession getInstance() {
        if (session == null) {
            session = new ImportSession();
        }
        return session;
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    /**
     * Returns whether (@code ImportSession) has been initialized with an excel spreadsheet
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     *  Opens excel file specified by (@code filepath) and initializes (@code SessionData) to support import operations
     */
    public void initializeSession(String filePath) throws FileAccessException, FileFormatException {
        if (inFile != null) {
            throw new FileAccessException("An excel file is already open.");
        }
        File file = new File (filePath);
        if (!file.exists()) {
            throw new FileAccessException("Please check the path to your file.");
        } else if (!file.canRead()) {
            throw new FileFormatException("Please enable file read permission.");
        }
        try {
            inWorkbook = createWorkBook(file);
            outWorkbook = createWorkBook(file);
        } catch (InvalidFormatException e) {
            throw new FileFormatException("Unable to read the format of file. "
                    + "Please ensure the file is in .xls or .xlsx format");
        } catch (IOException e) {
            throw new FileFormatException("Unable to read file. Please close the file and try again.");
        }
        // the file is good to go
        inFile = file;
        initializeSessionData();
    }

    /**
     * Attempts to parse the column headers and retrieve job entries
     */
    public void initializeSessionData() {
        sessionData = new SessionData();
        for (String field : JOB_ENTRY_COMPULSORY_FIELDS) {
            ;
        }
        for (String field : JOB_ENTRY_OPTIONAL_FIELDS) {
            ;
        }
    }

    /**
     * Attempts to create a (@code Workbook) for a given (@code File)
     */
    private Workbook createWorkBook(File file) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file);

        // Retrieving the number of sheets in the Workbook
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");
        return workbook;
    }

    /**
     * Flushes feedback to (@code outFile) and releases resources
     */
    public void closeSession() {
        if (!initialized) {
            return;
        }
        if (outFile == null) { // does not check if a file exists
            String timeStamp = getTimeStamp();
            outFile = new File(inFile.getName() + timeStamp);
        }
        // TODO: code to write outfile
        freeResources();
    }

    private void freeResources() {
        inFile = null;
        outFile = null;
    }

    /**
     * For localized testing purposes
     */
    public static void main(String[] args) {
        ImportSession importSession = getInstance();
        String path;
        try {
            path = new File(".").getCanonicalPath();
            System.out.println(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            importSession.initializeSession(
                    ".\\src\\test\\resources\\model.session.ImportSessionTest\\CS2103-testsheet.xlsx");
        } catch (FileAccessException e) {
            e.printStackTrace();
        } catch (FileFormatException e) {
            e.printStackTrace();
        }

    }
}
