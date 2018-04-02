package seedu.carvicim.storage.session;

import java.io.IOException;
import java.util.Optional;

import seedu.carvicim.commons.exceptions.DataConversionException;
import seedu.carvicim.model.UserPrefs;

/**
 * Represents a storage for {@link SessionData}.
 */
public interface SessionDataStorage {

    /**
     * Returns the file path of the UserPrefs data file.
     */
    String getUserPrefsFilePath();

    /**
     * Returns session data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<SessionData> readSessionData() throws DataConversionException, IOException;

    /**
     * Saves the given {@link SessionData} to the storage.
     * @param sessionData cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSessionData(SessionData sessionData) throws IOException;

}
