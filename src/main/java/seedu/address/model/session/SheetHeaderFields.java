package seedu.address.model.session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import seedu.address.model.session.exceptions.FileFormatException;

//@@uathor yuhongherald
/**
 * A data structure used to store header field information of a given excel sheet
 */
public class SheetHeaderFields extends HashMap<String, RowData> {
    public static final String[] JOB_ENTRY_COMPULSORY_FIELDS = { // ignore case when reading headings
        "Client name", "Client phone", "Client email", "Vehicle number", "Assigned Employees"
    };
    public static final String[] JOB_ENTRY_OPTIONAL_FIELDS = { // ignore case when reading headings
        "Job number", "Date", "Status", "Remarks"
    };
    public static final String INVALID_FIELD = "INVALID_FIELD";
    public static final String ERROR_MESSAGE_MISSING_FIELDS = "Missing header fields: ";

    private SheetHeaderFields(HashMap<String, RowData> fields) throws FileFormatException {
        this.putAll(fields);
    }

    /**
     * a
     * @param sheet
     * @return
     * @throws FileFormatException
     */
    public static SheetHeaderFields createHeaderField(Sheet sheet) throws FileFormatException {
        ArrayList<String> missingCompulsoryFields = new ArrayList<String>(Arrays.asList(JOB_ENTRY_COMPULSORY_FIELDS));
        ArrayList<String> missingOptionalFields = new ArrayList<String>(Arrays.asList(JOB_ENTRY_COMPULSORY_FIELDS));
        HashMap<String, RowData> headerFields = parseFirstRow(sheet, missingCompulsoryFields, missingOptionalFields);
        if (!missingCompulsoryFields.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder(ERROR_MESSAGE_MISSING_FIELDS);
            for (String field : missingCompulsoryFields) {
                stringBuilder.append(field);
            }
            throw new FileFormatException(stringBuilder.toString());
        }
        // TODO: Handle missing optional fields, using a different method
        return null;
    }

    /**
     * a
     * @param sheet
     * @param missingCompulsoryFields
     * @param missingOptionalFields
     * @return
     */
    private static HashMap<String, RowData> parseFirstRow(Sheet sheet,
            ArrayList<String> missingCompulsoryFields, ArrayList<String> missingOptionalFields) {
        HashMap<String, RowData> headerFields = new HashMap<>();
        Row firstRow = sheet.getRow(sheet.getFirstRowNum());
        DataFormatter dataFormatter = new DataFormatter();
        int lastFieldIndex = firstRow.getLastCellNum();
        String lastField = INVALID_FIELD;
        String currentField;
        // traverse the row from the back to assist detecting end of row
        for (int i = firstRow.getLastCellNum(); i <= firstRow.getFirstCellNum(); i--) {
            currentField = dataFormatter.formatCellValue(firstRow.getCell(i));
            if (currentField.equalsIgnoreCase(lastField)) {
                continue;
            }
            if (!isFieldPresent(currentField, missingCompulsoryFields, missingOptionalFields)) {
                lastField = INVALID_FIELD;
                lastFieldIndex = i - 1;
                continue;
            }
            // TODO: remove field from either compulsory or optional field
            headerFields.put(currentField, new RowData(i, lastFieldIndex));
            lastField = currentField;
            lastFieldIndex = i - 1;
        }
        return  headerFields;
    }

    /**
     * Checks if (@code field) is present in (@code fields), ignoring case
     */
    public static boolean isFieldPresent(String field, ArrayList<String> ... fields) {
        return false;
    }

    public ArrayList<String> readRowData() {
        return null;
    }
}
