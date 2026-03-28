package services;

import entities.*;
import interfaces.CarRentalService;
import services.validator.CarValidator;
import services.validator.CustomerValidator;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CarRentalServiceImpl implements CarRentalService {
    private static final byte MAX_ID_TRIES = 3;
    private Map<String, Car> carsById;
    private Map<String, Customer> customersById;
    private Map<String, Rental> rentalsById;

    public CarRentalServiceImpl() {
        carsById = new HashMap<String, Car>();
        customersById = new HashMap<String, Customer>();
        rentalsById = new HashMap<String, Rental>();
    }

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

    @Override
    public void addCustomer(String firstName, String lastName, String email) {
        if(!CustomerValidator.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address!");
        }
        if (!CustomerValidator.isValidName(firstName)) {
            throw new IllegalArgumentException("Invalid first name!");
        }
        if(!CustomerValidator.isValidName(lastName)) {
            throw new IllegalArgumentException("Invalid last name!");
        }

        for (int i = 0; i < MAX_ID_TRIES; i++) {
            Customer customer = new Customer(firstName, lastName, email);
            if(!customersById.containsKey(customer.getId())){
                customersById.put(customer.getId(), customer);
                return;
            }
        }
        throw new IllegalStateException("Customer ID collision: Could not allocate unique customer ID!");
    }

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

//        if(CarValidator.isValidType(type)){
            car.changeType(type);
//        } else {
//            throw new IllegalArgumentException("Invalid type!");
//        }

        car.setYear(year);
    }

    @Override
    public void removeCar(String carId) {
        Car carToRemove = findCarById(carId);
        carToRemove.removeFromFleet();
    }

    @Override
    public List<Car> getAllCars() {
        if(carsById.isEmpty()){
            throw new NoSuchElementException("No cars found!");
        } else {
            return new ArrayList<>(carsById.values());
        }
    }

    @Override
    public List<Car> getAvailableCars() {
        if(carsById.isEmpty()){
            throw new NoSuchElementException("No cars found!");
        }

        return carsById.values().stream().filter(Car::isAvailable).collect(Collectors.toList());
    }

    @Override
    public List<Car> findCarsByModel(String model) {
        if(carsById.isEmpty()){
            throw new NoSuchElementException("No cars found!");
        }

        List<Car> carsByModel = carsById.values()
                .stream()
                .filter(x-> x.getModel().equals(model))
                .collect(Collectors.toList());
        if(carsByModel.isEmpty()){
            throw new NoSuchElementException("No cars found!");
        }

        return carsByModel;
    }

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

    @Override
    public Car findCarById(String carId) {
        if(carsById.containsKey(carId.trim())){
            return carsById.get(carId);
        } else {
            throw new NoSuchElementException("Car with ID " + carId + " not found!");
        }
    }

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

    @Override
    public Rental returnCar(String carId, LocalDate actualReturnDate) {
        if(rentalsById.isEmpty()){
            throw new NoSuchElementException("No rentals found!");
        }
        Rental rental = findActiveRentalByCarId(carId);
        Car rentedCar = findCarById(carId);

        if(actualReturnDate == null) {
            throw new IllegalArgumentException("Return date cannot be null!");
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

    private Customer getCustomerById(String id) {
        if(customersById.isEmpty()){
            throw new NoSuchElementException("No customers found!");
        }
        if(!customersById.containsKey(id)){
            throw new NoSuchElementException("Customer with ID " + id + " not found!");
        }
        return customersById.get(id);
    }

    private Rental findActiveRentalByCarId(String carId) {
        for (Rental rental : rentalsById.values()) {
            if (rental.getCarId().equals(carId) && rental.isActive()) {
                return rental;
            }
        }
        throw new NoSuchElementException("No active rental found for car ID " + carId);
    }
}
