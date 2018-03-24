package seedu.address.model.session;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import seedu.address.model.session.exceptions.DataIndexOutOfBoundsException;

/**
 * Represents a field that spans one or more columns
 */
public class RowData implements ExcelColumnSpannable {
    private final int startIndex;
    private final int endIndex;

    RowData(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public int getStartIndex() {
        return startIndex;
    }

    @Override
    public int getEndIndex() {
        return endIndex;
    }

    @Override
    public ArrayList<String> readData(Workbook workbook, int sheetNumber, int rowNumber)
            throws DataIndexOutOfBoundsException {
        if (sheetNumber < 0 || sheetNumber >= workbook.getNumberOfSheets()) {
            throw new DataIndexOutOfBoundsException("Sheets", 0, workbook.getNumberOfSheets(), sheetNumber);
        }
        Sheet sheet = workbook.getSheetAt(sheetNumber);
        if (rowNumber < 0 || rowNumber >= sheet.getPhysicalNumberOfRows()) {
            throw new DataIndexOutOfBoundsException("Rows", 0, sheet.getPhysicalNumberOfRows(), sheetNumber);
        }
        Row row = sheet.getRow(rowNumber);
        ArrayList<String> data = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();
        for (int i = startIndex; i < endIndex; i++) {
            data.add(dataFormatter.formatCellValue(row.getCell(i)));
        }
        return data;
    }
}
