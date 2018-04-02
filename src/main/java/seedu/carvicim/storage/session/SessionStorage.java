package seedu.carvicim.storage.session;

import java.io.IOException;
import java.util.Optional;

import seedu.carvicim.commons.exceptions.DataConversionException;
import seedu.carvicim.model.Carvicim;
import seedu.carvicim.model.ReadOnlyCarvicim;

/**
 * Represents a storage for {@link Carvicim}.
 */
public interface SessionStorage {

    /**
     * Returns the file path of the data file.
     */
    String getsessionDataFilePath();

    /**
     * Returns SessionData data as a {@link SessionData}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<SessionData> readSessionData() throws DataConversionException, IOException;

    /**
     * @see #getsessionDataFilePath()
     */
    Optional<SessionData> readSessionData(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link SessionData} to the storage.
     * @param sessionData cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSessionData(SessionData sessionData) throws IOException;

    /**
     * @see #saveSessionData(SessionData)
     */
    void saveSessionData(SessionData sessionData, String filePath) throws IOException;

}
