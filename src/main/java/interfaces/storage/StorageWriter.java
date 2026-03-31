package interfaces.storage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

public interface StorageWriter<T> {
    void writeFile(BufferedWriter writer, Map<String, T> map) throws IOException;
}
