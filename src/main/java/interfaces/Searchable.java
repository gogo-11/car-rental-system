package interfaces;

import entities.Car;
import entities.CarStatus;

import java.util.List;

public interface Searchable {
    List<Car> findCarsByModel(String model);
    List<Car> findCarsByStatus(CarStatus status);
    Car findCarById(String carId);
}
