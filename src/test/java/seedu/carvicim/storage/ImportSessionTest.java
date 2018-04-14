package seedu.carvicim.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

import seedu.carvicim.logic.commands.exceptions.CommandException;
import seedu.carvicim.storage.session.ImportSession;
import seedu.carvicim.storage.session.SessionData;
import seedu.carvicim.storage.session.SheetParser;
import seedu.carvicim.storage.session.exceptions.FileAccessException;
import seedu.carvicim.storage.session.exceptions.FileFormatException;
import seedu.carvicim.storage.session.exceptions.InvalidDataException;

//@@author yuhongherald
public class ImportSessionTest {
    protected static final String RESOURCE_PATH = "storage/session/ImportSessionTest/";
    protected static final String ERROR_INPUT_FILE = "CS2103-testsheet.xlsx";
    protected static final String ERROR_RESULT_FILE = "CS2103-testsheet-results.xlsx";
    protected static final String ERROR_OUTPUT_FILE = "CS2103-testsheet-comments.xlsx";
    protected static final String MULTIPLE_INPUT_FILE = "CS2103-testsheet-multiple.xlsx";
    protected static final String MULTIPLE_RESULT_FILE = "CS2103-testsheet-multiple-results.xlsx";
    protected static final String MULTIPLE_OUTPUT_FILE = "CS2103-testsheet-multiple-comments.xlsx";
    protected static final String CORRUPT_INPUT_FILE = "CS2103-testsheet-corrupt.xlsx";
    protected static final String CORRUPT_RESULT_FILE = "CS2103-testsheet-corrupt-results.xlsx";
    protected static final String CORRUPT_OUTPUT_FILE = "CS2103-testsheet-corrupt-comments.xlsx";
    protected static final String NON_EXCEL_FILE = "non-excel-file.xlsx";
    protected static final String MISSING_HEADER_FIELD_FILE = "missing_header_field.xlsx";

    private static final String EMPLOYEE_EMAIL_FIELD = "employee email, ";

    protected String inputPath;
    protected String outputPath;
    protected String resultPath;
    private File testFile;
    private File outputFile;
    private File expectedOutputFile;
    private String outputFilePath;
    private String expectedOutputPath;

    @Test
    public void import_testFileWithErrorCorrection_success() throws Exception {
        setup(ERROR_INPUT_FILE, ERROR_RESULT_FILE, ERROR_OUTPUT_FILE);
        importAll();
        assertOutputResultEqual();
        cleanup();
    }

    @Test
    public void import_testFileWithMultipleSheets_success() throws Exception {
        setup(MULTIPLE_INPUT_FILE, MULTIPLE_RESULT_FILE, MULTIPLE_OUTPUT_FILE);
        importAll();
        assertOutputResultEqual();
        cleanup();
    }

    @Test
    public void import_testFileWithCorruptEntry_success() throws Exception {
        setup(CORRUPT_INPUT_FILE, CORRUPT_RESULT_FILE, CORRUPT_OUTPUT_FILE);
        importAll();
        assertOutputResultEqual();
        cleanup();
    }

    @Test
    public void import_missingEmployeeEmail_failure() throws Exception {
        setup(MISSING_HEADER_FIELD_FILE, MISSING_HEADER_FIELD_FILE, null);
        try {
            importAll();
        } catch (InvalidDataException e) {
            assertEquals(SheetParser.ERROR_MESSAGE_MISSING_FIELDS + EMPLOYEE_EMAIL_FIELD, e.getMessage());
        }
    }

    @Test
    public void import_notExcelSheet_failure() throws Exception {
        setup(NON_EXCEL_FILE, NON_EXCEL_FILE, null);
        try {
            importAll();
        } catch (FileFormatException e) {
            assertEquals(SessionData.ERROR_MESSAGE_FILE_FORMAT, e.getMessage());
        }
    }

    protected void cleanup() throws IOException {
        deleteFile(outputFilePath);
    }

    /**
     * asserts output file from importAll of input file is the same as result file
     */
    protected void assertOutputResultEqual() throws Exception {
        importAll();
        assertEquals(expectedOutputFile.getAbsolutePath(), outputFile.getAbsolutePath());
        ImportSession.getInstance().getSessionData().freeResources();
        assertExcelFilesEquals(testFile, outputFile);
    }

    /**
     * Imports all job entries from excel file by accepting them
     */
    private void importAll() throws FileAccessException, FileFormatException, CommandException, InvalidDataException {
        ClassLoader classLoader = getClass().getClassLoader();
        ImportSession importSession = ImportSession.getInstance();
        File inputFile = new File(inputPath);
        importSession.initializeSession(inputFile.getPath());
        importSession.getSessionData().reviewAllRemainingJobEntries(true, "");
        outputFilePath = importSession.closeSession();
        outputPath = classLoader.getResource(expectedOutputPath).getPath();
        testFile = new File(resultPath);
        outputFile = new File(outputFilePath);
        expectedOutputFile = new File(outputPath);
    }

    protected void setup(String inputPath, String resultPath, String outputPath) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        this.inputPath = classLoader.getResource(RESOURCE_PATH + inputPath).getPath();
        this.resultPath = classLoader.getResource(RESOURCE_PATH + resultPath).getPath();
        this.expectedOutputPath = RESOURCE_PATH + outputPath;
        try {
            this.outputPath = classLoader.getResource(RESOURCE_PATH + outputPath).getPath();
            deleteFile(this.outputPath);
        } catch (NullPointerException e) {
            ;
        }
    }

    /**
     * Asserts that 2 excel files are equal:
     * The have the same number of sheets
     * In each sheet, they have the same number of rows
     * In each row, they have the same number of columns
     * In each cell, they have the same content
     */
    private void assertExcelFilesEquals(File file1, File file2) throws IOException, InvalidFormatException {
        Workbook workbook1 = WorkbookFactory.create(file1);
        Workbook workbook2 = WorkbookFactory.create(file2);
        assertEquals(workbook1.getNumberOfSheets(), workbook2.getNumberOfSheets());
        Sheet sheet1;
        Sheet sheet2;
        for (int i = 0; i < workbook1.getNumberOfSheets(); i++) {
            sheet1 = workbook1.getSheetAt(workbook1.getFirstVisibleTab() + i);
            sheet2 = workbook2.getSheetAt(workbook1.getFirstVisibleTab() + i);
            assertSheetEquals(sheet1, sheet2);
        }
    }

    /**
     * Asserts that 2 sheets are equal:
     * In each sheet, they have the same number of rows
     * In each row, they have the same number of columns
     * In each cell, they have the same content
     */
    private void assertSheetEquals(Sheet sheet1, Sheet sheet2) {
        assertEquals(sheet1.getPhysicalNumberOfRows(), sheet2.getPhysicalNumberOfRows());
        Row row1;
        Row row2;
        for (int i = sheet1.getFirstRowNum(); i <= sheet1.getLastRowNum(); i++) {
            row1 = sheet1.getRow(i);
            row2 = sheet2.getRow(i);
        }
    }

    /**
     * Asserts that 2 rows are equal:
     * In each row, they have the same number of columns
     * In each cell, they have the same content
     */
    private void assertRowEquals(Row row1, Row row2) {
        assertEquals(row1.getPhysicalNumberOfCells(), row2.getPhysicalNumberOfCells());
        Cell cell1;
        Cell cell2;
        for (int i = row1.getFirstCellNum(); i <= row1.getLastCellNum(); i++) {
            cell1 = row1.getCell(i);
            cell2 = row2.getCell(i);
        }
    }

    /**
     * Asserts that 2 cells are equal:
     * In each cell, they have the same content
     */
    private void assertCellEquals(Cell cell1, Cell cell2) {
        DataFormatter dataFormatter = new DataFormatter();
        assertEquals(dataFormatter.formatCellValue(cell1), dataFormatter.formatCellValue(cell2));
    }

    /**
     * Deletes file at (@String filePath) if file exists
     */
    private void deleteFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
