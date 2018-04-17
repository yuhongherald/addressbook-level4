package seedu.carvicim.storage.session;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import seedu.carvicim.storage.session.exceptions.DataIndexOutOfBoundsException;

//@@author yuhongherald
/**
 * Represents a field that spans one or more columns
 */
public class RowData {
    private final int startIndex;
    private final int endIndex;

    public RowData(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    /**
     * Reads all the entries between {@code startIndex} and {@code endIndex} from a row in the excel file
     */
    public ArrayList<String> readDataFromSheet(Sheet sheet, int rowNumber)
            throws DataIndexOutOfBoundsException {
        if (rowNumber < sheet.getFirstRowNum() || rowNumber > sheet.getLastRowNum()) {
            throw new DataIndexOutOfBoundsException(sheet.getFirstRowNum(), sheet.getLastRowNum(), rowNumber);
        }
        Row row = sheet.getRow(rowNumber);
        ArrayList<String> data = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();
        for (int i = startIndex; i <= endIndex; i++) {
            data.add(dataFormatter.formatCellValue(row.getCell(i)));
        }
        return data;
    }
}
