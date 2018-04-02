package seedu.carvicim.storage.session;

import java.io.IOException;
import java.util.Optional;

import seedu.carvicim.commons.exceptions.DataConversionException;
import seedu.carvicim.commons.util.JsonUtil;
import seedu.carvicim.model.UserPrefs;
import seedu.carvicim.storage.UserPrefsStorage;

/**
 * A class to access UserPrefs stored in the hard disk as a json file
 */
public class JsonSessionDataStorage implements SessionDataStorage {

    private String filePath;

    public JsonSessionDataStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getUserPrefsFilePath() {
        return filePath;
    }

    @Override
    public Optional<SessionData> readSessionData() throws DataConversionException, IOException {
        return readUserPrefs(filePath);
    }

    /**
     * Similar to {@link #readSessionData()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<SessionData> readUserPrefs(String prefsFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(prefsFilePath, SessionData.class);
    }

    @Override
    public void saveSessionData(SessionData sessionData) throws IOException {
        JsonUtil.saveJsonFile(sessionData, filePath);
    }

}
