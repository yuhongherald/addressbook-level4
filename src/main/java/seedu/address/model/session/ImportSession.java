package seedu.address.model.session;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import seedu.address.model.session.exceptions.FileAccessException;
import seedu.address.model.session.exceptions.FileFormatException;

//@@author yuhongherald
/**
 * Used to store data relevant to importing of (@code Job) from (@code inFile) and
 * exporting (@code Job) with commens to (@code outFile). Implements a Singleton design pattern.
 */
public class ImportSession {
    private static ImportSession session;

    private boolean initialized;
    private File inFile;
    private Workbook workbook; // write comments to column after last row, with approval status
    private SessionData sessionData;
    private File outFile;

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
            workbook = createWorkBook(file);
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
    private void initializeSessionData() throws FileFormatException {
        SheetWithHeaderFields sheetWithHeaderFields;
        SheetParser sheetParser;
        Sheet sheet;
        sessionData = new SessionData();

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            sheet = workbook.getSheetAt(workbook.getFirstVisibleTab() + i);
            sheetParser = new SheetParser(sheet);
            sheetWithHeaderFields = sheetParser.parseSheetWithHeaderField();
            sessionData.addUnreviewedJobEntries(sheetWithHeaderFields);
        }
    }

    /**
     * Attempts to create a (@code Workbook) for a given (@code File)
     */
    private Workbook createWorkBook(File file) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file);
        return workbook;
    }

    /**
     * Flushes feedback to (@code outFile) and releases resources. Currently not persistent.
     */
    public void closeSession() {
        if (!initialized) {
            return;
        }
        if (outFile == null) { // does not check if a file exists
            String timeStamp = getTimeStamp();
            outFile = new File(inFile.getName() + timeStamp);
        }
        // TODO: code to write workbook outfile
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
