package interfaces.storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * Interface for reading data from storage
 * @param <T> the type of objects to be read from storage
 */
public interface StorageReader<T> {
    /**
     * Reads a file and returns a map collection with the objects extracted from the file
     * The key of the map is the ID of the object.
     *
     * @param bufferedReader the reader to read the file
     * @return the map with the objects extracted from the file
     * @throws IOException if an I/O error occurs while reading the file
     */
    Map<String, T> readFile(BufferedReader bufferedReader) throws IOException;
}
