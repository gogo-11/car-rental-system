package storage.writer;

import interfaces.storage.StorageWriter;

import java.io.BufferedWriter;
import java.io.IOException;


/**
 * Abstract class that serves as a base for all storage writers
 * @param <T> the type of objects to be written in the storage
 */
public abstract class AbstractStorageWriter<T> implements StorageWriter<T> {
    protected abstract String getColumns();

    /**
     * Writes the first line of the csv file, which includes the columns' names
     * @param writer the writer to handle the write operation
     * @throws IOException if an IO error occurs while writing in the file
     */
    public void writeFirstLine(BufferedWriter writer) throws IOException {
        writer.write(getColumns());
        writer.write("\n");
    }
}
