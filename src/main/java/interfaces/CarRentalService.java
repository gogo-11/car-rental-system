package interfaces;

import entities.*;

import java.time.LocalDate;
import java.util.List;

public interface CarRentalService extends Searchable{
    void addCar(String make, String model, int year, CarType type);
    void addCustomer(String firstName, String lastName, String email);
    void editCar(String carId, String make, String model, int year, CarType type);
    void removeCar(String id);

    List<Car> getAllCars();
    List<Car> getAvailableCars();
//    List<Car> findCarsByModel(String model);
//    List<Car> findCarsByStatus(CarStatus status);
//    Car findCarById(String carId);

    Rental rentCar(String carId, String customerId, LocalDate expectedReturnDate);
    Rental returnCar(String carId, LocalDate actualReturnDate);
}
