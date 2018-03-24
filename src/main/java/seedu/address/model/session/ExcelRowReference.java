package seedu.address.model.session;

/**
 *
 */
public interface ExcelRowReference {

    /**
     * Returns the excel sheet number of this element.
     */
    public int getSheetNumber();

    /**
     * Returns the excel row number of this element
     * @return
     */
    public int getRowNumber();
}
