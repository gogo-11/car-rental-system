package storage;

import entities.Car;
import entities.CarStatus;
import entities.CarType;
import interfaces.CarStorageReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class CarStorageReaderImpl implements CarStorageReader {
    private static final String COMMA_DELIMITER = ",";
    private static final String[] EXPECTED_COLUMNS = {"Id","Make","Model","Year","Type","Status","CurrentRenterId"};

    @Override
    public Map<String, Car> readCarsFile(BufferedReader bufferedReader) throws IOException{

        readAndValidateFirstLine(bufferedReader);

        Map<String, Car> cars = new HashMap<>();
        String line;

        while((line = bufferedReader.readLine()) != null) {
            if(!line.isBlank()){
                String[] values = line.split(COMMA_DELIMITER,-1);
                if(values.length == 7){
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

    private void readAndValidateFirstLine(BufferedReader bufferedReader) throws IOException {
//        FileReader fileReader = new FileReader("database/cars.csv");
//        bufferedReader = new BufferedReader(fileReader);
        String firstLine = bufferedReader.readLine();
        if (firstLine == null || firstLine.trim().isEmpty()) {
            throw new IllegalArgumentException("CSV file is empty or missing first line.");
        }

        String[] columns = firstLine.split(COMMA_DELIMITER,-1);
        if(!areColumnsValid(columns)) {
            throw new IllegalArgumentException("Invalid first line in cars CSV file. Expected: " +
                    String.join(",", EXPECTED_COLUMNS));
        }

//        return columns;
    }

    private boolean areColumnsValid(String[] cols){
        if(cols ==null || cols.length != EXPECTED_COLUMNS.length){
            return false;
        }

        for (int i = 0; i < EXPECTED_COLUMNS.length; i++) {
            String element;
            if(cols[i] == null){
                element = "";
            } else {
                element = cols[i].trim();
            }
            if(!EXPECTED_COLUMNS[i].equalsIgnoreCase(element)){
                return false;
            }
        }
        return true;
    }
}
