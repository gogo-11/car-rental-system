package services;

import entities.*;
import interfaces.CarRentalService;
import services.validator.CarValidator;
import services.validator.CustomerValidator;
import services.validator.Validator;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CarRentalServiceImpl implements CarRentalService {
    private static final byte MAX_ID_TRIES = 3;
    private Map<String, Car> carsById;
    private Map<String, Customer> customersById;
    private Map<String, Rental> rentalsById;
    private Map<String, Customer> customersByEmail;

    public CarRentalServiceImpl() {
        carsById = new HashMap<>();
        customersById = new HashMap<>();
        rentalsById = new HashMap<>();
        customersByEmail = new HashMap<>();
    }

    /**
     *
     * @throws IllegalStateException when a unique car ID fails to be generated
     */
    @Override
    public void addCar(String make, String model, int year, CarType type) {
        for (int i = 0; i < MAX_ID_TRIES; i++) {
            Car car = new Car(make, model, year, type);
            if(!carsById.containsKey(car.getId())){
                carsById.put(car.getId(), car);
                return;
            }
        }
        throw new IllegalStateException("Car ID collision: Could not allocate unique car ID!");
    }

    /**
     *
     * @throws IllegalStateException if a unique car ID fails to be generated
     * @throws IllegalStateException if the specified email is already used
     */
    @Override
    public Customer addCustomer(String firstName, String lastName, String email) {
        if(!CustomerValidator.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address!");
        }
        if (!CustomerValidator.isValidName(firstName)) {
            throw new IllegalArgumentException("Invalid first name!");
        }
        if(!CustomerValidator.isValidName(lastName)) {
            throw new IllegalArgumentException("Invalid last name!");
        }
        if(customersByEmail.containsKey(email.trim().toLowerCase())) {
            throw new IllegalStateException("Customer with this email already exists!");
        }

        for (int i = 0; i < MAX_ID_TRIES; i++) {
            Customer customer = new Customer(firstName, lastName, email);
            if(!customersById.containsKey(customer.getId())){
                customersById.put(customer.getId(), customer);
                customersByEmail.put(customer.getEmail(), customer);
                return customer;
            }
        }
        throw new IllegalStateException("Customer ID collision: Could not allocate unique customer ID!");
    }

    /**
     *
     * @throws NoSuchElementException when a car with the specified ID is not found
     * @throws IllegalArgumentException when the make or the model passed are invalid
     */
    @Override
    public void editCar(String carId, String make, String model, int year, CarType type) {
        Car car;
        if(carsById.containsKey(carId)){
            car = carsById.get(carId);
        } else {
            throw new NoSuchElementException("Car with ID " + carId + " not found!");
        }

        if(CarValidator.isValidName(make)){
            car.setMake(make);
        } else {
            throw new IllegalArgumentException("Invalid make name!");
        }

        if(CarValidator.isValidName(model)){
            car.setModel(model);
        } else {
            throw new IllegalArgumentException("Invalid model name!");
        }

        car.changeType(type);
        car.setYear(year);
    }

    @Override
    public void removeCar(String carId) {
        Car carToRemove = findCarById(carId);
        carToRemove.removeFromFleet();
    }

    /**
     *
     * @return all cars
     * @throws NoSuchElementException when cars are not found
     */
    @Override
    public List<Car> getAllCars() {
        if(carsById.isEmpty()){
            throw new NoSuchElementException("No cars found!");
        } else {
            return new ArrayList<>(carsById.values());
        }
    }

    /**
     *
     * @throws NoSuchElementException when no available cars are found
     */
    @Override
    public List<Car> getAvailableCars() {
        if(carsById.isEmpty()){
            throw new NoSuchElementException("No cars found!");
        }

        return carsById.values().stream().filter(Car::isAvailable).collect(Collectors.toList());
    }

    /**
     * Returns a list of cars matching the model specified by the user
     * @param model the car model name
     * @return list of Car objects matching the model specified
     * @throws NoSuchElementException when no matching cars are found
     */
    @Override
    public List<Car> findCarsByModel(String model) {
        if(carsById.isEmpty()){
            throw new NoSuchElementException("No cars found!");
        }

        String query = Validator.requireNonBlank(model, "model").toLowerCase();

        List<Car> carsByModel = carsById.values()
                .stream()
                .filter(x-> x.getModel().toLowerCase().contains(query))
                .collect(Collectors.toList());
        if(carsByModel.isEmpty()){
            throw new NoSuchElementException("No cars found!");
        }

        return carsByModel;
    }

    /**
     * Returns a list of cars matching the car status specified by the user
     *
     * @param status the status of the car - AVAILABLE, RENTED, REMOVED
     * @return list of Car objects matching the status specified
     * @throws NoSuchElementException when no matching cars are found
     */
    @Override
    public List<Car> findCarsByStatus(CarStatus status) {
        if(carsById.isEmpty()){
            throw new NoSuchElementException("No cars found!");
        }

        List<Car> carsByStatus = carsById.values()
                .stream()
                .filter(x -> x.getStatus().equals(status))
                .collect(Collectors.toList());
        if(carsByStatus.isEmpty()){
            throw new NoSuchElementException("No cars found!");
        }

        return carsByStatus;
    }

    /**
     *
     * @param carId the ID of the car to be searched
     * @return Car object of the searched car
     * @throws NoSuchElementException when no car with the specified ID is found
     */
    @Override
    public Car findCarById(String carId) {
        if(carsById.containsKey(carId.trim())){
            return carsById.get(carId.trim());
        } else {
            throw new NoSuchElementException("Car with ID " + carId + " not found!");
        }
    }

    /**
     *
     * @throws NoSuchElementException when no car or customer with the specified IDs are found
     * @throws IllegalStateException when the car with the specified ID is not available for rent
     * @throws DateTimeException when the return date specified is before the rent date
     */
    @Override
    public Rental rentCar(String carId, String customerId, LocalDate expectedReturnDate) {
        if(!carsById.containsKey(carId)){
            throw new NoSuchElementException("Car with ID " + carId + " not found!");
        }
        if(!findCarById(carId).isAvailable()){
            throw new IllegalStateException("Car with ID " + carId + " not available for rent!");
        }
        if(!customersById.containsKey(customerId)){
            throw new NoSuchElementException("Customer with ID " + customerId + " not found!");
        }
        if(expectedReturnDate.isBefore(LocalDate.now())) {
            throw new DateTimeException("Return date cannot be a past date!");
        }


        Car car = carsById.get(carId);
        Customer customer = getCustomerById(customerId);
        car.rent(customer.getId());

        Rental newRental = new Rental(carId,customerId,expectedReturnDate);
        rentalsById.put(newRental.getId(), newRental);
        return newRental;
    }

    /**
     *
     * @throws NoSuchElementException when no rentals are found
     * @throws IllegalArgumentException when the return date specified is null or empty
     */
    @Override
    public Rental returnCar(String carId, LocalDate actualReturnDate) {
        if(rentalsById.isEmpty()){
            throw new NoSuchElementException("No rentals found!");
        }
        Rental rental = findActiveRentalByCarId(carId);
        Car rentedCar = findCarById(carId);

        if(actualReturnDate == null || actualReturnDate.toString().trim().isEmpty()) {
            throw new IllegalArgumentException("Return date cannot be null or empty!");
        }

        rental.markAsCompleted(actualReturnDate);
        rentedCar.returnFromRental();

        return rental;
    }

    private CarType getValidType(String type){
        try{
            return CarType.valueOf(type.trim().toUpperCase().replace(' ','_'));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid type!");
        }
    }

    /**
     *
     * @param id the ID of the customer you want to get
     * @return the found by ID customer
     * @throws NoSuchElementException when the customer is not found
     */
    public Customer getCustomerById(String id) {
        if(customersById.isEmpty()){
            throw new NoSuchElementException("No customers found!");
        }
        if(!customersById.containsKey(id)){
            throw new NoSuchElementException("Customer with ID " + id + " not found!");
        }
        return customersById.get(id);
    }

    public String getCustomerEmail(String customerId){
        return customersById.get(customerId).getEmail();
    }

    /**
     *
     * @param carId the ID of the car
     * @return the active rental associated with the car
     * @throws NoSuchElementException if no active rental exists for the given car ID
     */
    private Rental findActiveRentalByCarId(String carId) {
        for (Rental rental : rentalsById.values()) {
            if (rental.getCarId().equals(carId) && rental.isActive()) {
                return rental;
            }
        }
        throw new NoSuchElementException("No active rental found for car ID " + carId);
    }

    @Override
    public void loadCars(Map<String, Car> cars) {
        if (cars == null) {
            return;
        }

        this.carsById = new HashMap<>(cars);
    }
}
