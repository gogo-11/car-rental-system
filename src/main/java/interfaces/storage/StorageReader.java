package interfaces.storage;

import entities.Car;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public interface StorageReader<T> {
    Map<String, T> readFile(BufferedReader bufferedReader) throws IOException;
}
