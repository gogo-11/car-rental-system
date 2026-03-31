package storage.reader;

import entities.Car;
import entities.CarStatus;
import entities.CarType;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class CarStorageReader extends AbstractStorageReader<Car> {
    private static final String COMMA_DELIMITER = ",";
    private static final String[] EXPECTED_COLUMNS = {"Id","Make","Model","Year","Type","Status","CurrentRenterId"};
    private static final int COLUMN_NUMBER = EXPECTED_COLUMNS.length;

    @Override
    public Map<String, Car> readFile(BufferedReader bufferedReader) throws IOException{

        readAndValidateFirstLine(bufferedReader, "cars.csv");

        Map<String, Car> cars = new HashMap<>();
        String line;

        while((line = bufferedReader.readLine()) != null) {
            if(!line.isBlank()){
                String[] values = line.split(COMMA_DELIMITER,-1);
                if(values.length == COLUMN_NUMBER){
                    String id = values[0].trim();
                    String make = values[1].trim();
                    String model = values[2].trim();
                    int year = Integer.parseInt(values[3].trim());
                    CarType type = CarType.valueOf(values[4].trim().toUpperCase());
                    CarStatus status = CarStatus.valueOf(values[5].trim().toUpperCase());
                    String currentRenterId = values[6].trim().isEmpty() ? null : values[6].trim();

                    Car car = Car.restoreCar(id, make, model, year, type, status, currentRenterId);
                    if(cars.containsKey(id)){
                        throw new IllegalStateException("Car with id " + id + " already exists");
                    }
                    cars.put(id, car);
                } else {
                    throw new IllegalStateException("The read line is invalid" + line);
                }
            }
        }

        return cars;
    }

    /**
     * @return the expected columns string array
     */
    @Override
    protected String[] expectedColumns() {
        return EXPECTED_COLUMNS;
    }
}
