package interfaces.service;

import entities.*;
import interfaces.Searchable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Main service interface for car rental operations
 * Defines car/customer management and rental management
 */
public interface CarRentalService extends Searchable {
    /**
     * Adds a new car to the system
     *
     * @param make the make name of the car
     * @param model the model name of the car
     * @param year the year of manufacturing of the car
     * @param type the body type of the car
     */
    void addCar(String make, String model, int year, CarType type);

    /**
     * Adds a new customer to the system
     *
     * @param email the email of the customer
     * @param firstName the first name of the customer
     * @param lastName the last name of the customer
     */
    Customer addCustomer(String firstName, String lastName, String email);

    /**
     * Updates car information by ID
     *
     * @param carId the ID of the car
     * @param make the make name of the car
     * @param model the model name of the car
     * @param year the year of manufacturing of the car
     * @param type the body type of the car
     */
    void editCar(String carId, String make, String model, int year, CarType type);

    /**
     * Marks a car as removed from the car fleet
     *
     * @param id the ID of the car to be removed
     */
    void removeCar(String id);

    /**
     * @return all cars currently stored in the system
     */
    List<Car> getAllCars();

    /**
     * @return all cars with status AVAILABLE
     */
    List<Car> getAvailableCars();

    /**
     * Creates a new rental for a car and customer by their ID's
     *
     * @param carId ID of the car to rent
     * @param customerId ID of the customer renting the car
     * @param expectedReturnDate the planned return date
     * @return the newly created rental record
     */
    Rental rentCar(String carId, String customerId, LocalDate expectedReturnDate);

    /**
     * Returns a car and completes its rental
     *
     * @param carId ID of the rented car
     * @param actualReturnDate actual date of return
     * @return the completed rental record
     */
    Rental returnCar(String carId, LocalDate actualReturnDate);

    /**
     * Loads the cars from the CSV to the carsById map
     * @param cars the map to load
     */
    void loadCars(Map<String, Car> cars);

    /**
     * Loads the customers from the CSV to the customersById map
     * @param customers the map to load
     */
    void loadCustomers(Map<String, Customer> customers);

    /**
     *
     * @return a list of all customers
     */
    List<Customer> listCustomers();

    /**
     *
     * @param email the email used for search
     * @return customer the customer found after performing the search
     */
    Customer findCustomerByEmail(String email);

    /**
     *
     * @param loadedRentals map of rentals loaded from csv file
     */
    void loadRentals(Map<String, Rental> loadedRentals);

    /**
     *
     * @return a list of all rentals
     */
    List<Rental> listRentals();
}
