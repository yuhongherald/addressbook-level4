package seedu.address.model.session;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Workbook;

import seedu.address.model.session.exceptions.DataIndexOutOfBoundsException;

/**
 * For fields that resides in one or more columns
 */
public interface ExcelColumnSpannable {
    public int getStartIndex();
    public int getEndIndex();
    public ArrayList<String> readData(Workbook workbook, int sheetNumber, int rowNumber)
            throws DataIndexOutOfBoundsException;
}
