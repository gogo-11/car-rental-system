package interfaces;

import entities.Car;
import entities.CarStatus;

import java.util.List;

/**
 * Interface which marks an object as searchable
 *
 * Every class implementing the method should provide logic for searching by model, status and ID
 */
public interface Searchable {
    List<Car> findCarsByModel(String model);
    List<Car> findCarsByStatus(CarStatus status);
    Car findCarById(String carId);
}
