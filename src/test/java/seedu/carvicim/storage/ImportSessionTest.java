package seedu.carvicim.storage;

import org.junit.Test;

import seedu.carvicim.storage.session.ImportSession;

//@@author yuhongherald
public class ImportSessionTest {
    private static final String TEST_INPUT_FILE = "storage/session/ImportSessionTest/CS2103-testsheet.xlsx";
    private static final String TEST_OUTPUT_FILE = "";

    @Test
    public void importTestFileWithErrorCorrection() throws Exception {
        ImportSession importSession = ImportSession.getInstance();

        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource(TEST_INPUT_FILE)
                .getPath();
        importSession.initializeSession(path);
        importSession.reviewAllRemainingJobEntries(true);
        importSession.closeSession();
    }

    private void compareExcelFiles() {
        ;
    }

    private void deleteFile(String filePath) {
        ;
    }
}
