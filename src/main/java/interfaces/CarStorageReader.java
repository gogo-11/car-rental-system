package interfaces;

import entities.Car;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public interface CarStorageReader {
    Map<String, Car> readCarsFile(BufferedReader bufferedReader) throws IOException;
}
