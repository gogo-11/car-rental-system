package interfaces;

import entities.Car;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

public interface CarStorageWriter {
    void writeCarsFile(BufferedWriter writer, Map<String, Car> cars) throws IOException;
}
