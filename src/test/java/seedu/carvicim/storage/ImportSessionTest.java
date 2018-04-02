package seedu.carvicim.storage;

import java.io.File;

import org.junit.Test;

import seedu.carvicim.storage.session.ImportSession;

//@@author yuhongherald
public class ImportSessionTest {
    @Test
    public void importTestFileWithErrorCorrection() throws Exception {
        ImportSession importSession = ImportSession.getInstance();
        String path;
        path = new File(".").getCanonicalPath();
        importSession.initializeSession(
                path + "\\src\\test\\resources\\model.session.ImportSessionTest\\CS2103-testsheet.xlsx");
        importSession.reviewAllRemainingJobEntries(true);
        importSession.closeSession();
    }

    private void compareExcelFiles() {

    }

    private void deleteFile(File file) {
        ;
    }
}
