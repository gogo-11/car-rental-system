package storage.writer;

import entities.Car;
import interfaces.storage.StorageReader;
import interfaces.storage.StorageWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

public abstract class AbstractStorageWriter<T> implements StorageWriter<T> {
    protected abstract String getColumns();

    public void writeFirstLine(BufferedWriter writer, Map<String, T> map) throws IOException {
        writer.write(getColumns());
        writer.write("\n");
    }
}
