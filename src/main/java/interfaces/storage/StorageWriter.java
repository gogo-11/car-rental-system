package interfaces.storage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Interface for writing data to storage
 *
 * @param <T> The type of objects to be written in the storage
 */
public interface StorageWriter<T> {

    /**
     * Writes the content of the provided map of objects to the
     *
     * @param writer the writer object which handles the write operations
     * @param map the map of objects to be written in the file
     * @throws IOException if an I/O error occurs while writing in the file
     */
    void writeFile(BufferedWriter writer, Map<String, T> map) throws IOException;
}
