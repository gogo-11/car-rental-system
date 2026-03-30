package storage;

import entities.Car;
import interfaces.CarStorageWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

public class CarStorageWriterImpl implements CarStorageWriter {
    private static final String COLUMNS = "Id,Make,Model,Year,Type,Status,CurrentRenterId";

    @Override
    public void writeCarsFile(BufferedWriter writer, Map<String, Car> cars) throws IOException {
        writer.write(COLUMNS);
        writer.write("\n");

        for (Car car : cars.values()){
            String currentRenterId;
            if(car.getCurrentRenterId() == null){
                currentRenterId = "";
            } else {
                currentRenterId = car.getCurrentRenterId();
            }

            String carRow = String.join(",", car.getId(), car.getMake(),
                    car.getModel(), String.valueOf(car.getYear()),
                    car.getType().name(), car.getStatus().name(), currentRenterId);

            writer.write(carRow);
            writer.newLine();
        }

    }
}
