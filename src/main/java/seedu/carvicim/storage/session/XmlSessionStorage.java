package seedu.carvicim.storage.session;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.carvicim.commons.core.LogsCenter;
import seedu.carvicim.commons.exceptions.DataConversionException;
import seedu.carvicim.commons.exceptions.IllegalValueException;
import seedu.carvicim.commons.util.FileUtil;
import seedu.carvicim.storage.XmlFileStorage;

/**
 * A class to access Carvicim data stored as an xml file on the hard disk.
 */
public class XmlSessionStorage implements SessionStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlSessionStorage.class);

    private String filePath;

    public XmlSessionStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getsessionDataFilePath() {
        return filePath;
    }

    @Override
    public Optional<SessionData> readSessionData() throws DataConversionException, IOException {
        return readSessionData(filePath);
    }

    /**
     * Similar to {@link #readSessionData()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<SessionData> readSessionData(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        File sessionDataFile = new File(filePath);

        if (!sessionDataFile.exists()) {
            logger.info("Session data file "  + sessionDataFile + " not found");
            return Optional.empty();
        }

        XmlSerializableSessionData xmlSessionData = XmlFileStorage.loadDataFromSessionDataFile(new File(filePath));
        try {
            return Optional.of(xmlSessionData.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + sessionDataFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveSessionData(SessionData sessionData) throws IOException {
        saveSessionData(sessionData, filePath);
    }

    /**
     * Similar to {@link #saveSessionData(SessionData)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveSessionData(SessionData sessionData, String filePath) throws IOException {
        requireNonNull(sessionData);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableSessionData(sessionData));
    }

}
