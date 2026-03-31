package storage.writer;

import entities.Car;
import interfaces.storage.StorageWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

public class CarStorageWriter extends AbstractStorageWriter<Car> {
    private static final String COLUMNS = "Id,Make,Model,Year,Type,Status,CurrentRenterId";

    @Override
    public void writeFile(BufferedWriter writer, Map<String, Car> cars) throws IOException {
        writeFirstLine(writer,cars);

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

    /**
     * @return the columns string
     */
    @Override
    protected String getColumns() {
        return COLUMNS;
    }
}
