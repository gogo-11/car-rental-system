package entities;

import interfaces.Rentable;
import services.validator.Validator;

import java.util.UUID;

/**
 * Represents a car in the rental system.
 * Stores car details and manages rental-related operations
 */
public class Car implements Rentable {
    private final String id;
    private String make;
    private String model;
    private int year;
    private CarType type;
    private CarStatus status;
    private String currentRenterId;

    public Car(String make, String model, int year, CarType type) {
        this.id = UUID.randomUUID().toString();
        this.make = Validator.requireNonBlank(make, "make");
        this.model = Validator.requireNonBlank(model, "model");
        this.year = validateYear(year);
        this.type = Validator.requireNonNull(type, "Car type");
        this.status = CarStatus.AVAILABLE;
    }

    private Car(String id, String make, String model, int year, CarType type, CarStatus status, String currentRenterId) {
        this.id = Validator.requireNonBlank(id, "id");
        this.make = Validator.requireNonBlank(make, "make");
        this.model = Validator.requireNonBlank(model, "model");
        this.year = validateYear(year);
        this.type = Validator.requireNonNull(type, "Car type");
        this.status = status == null ? CarStatus.AVAILABLE : status;
        this.currentRenterId = validateCurrentRenterId(currentRenterId);
    }

    public String getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = Validator.requireNonBlank(make, "make");
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = Validator.requireNonBlank(model, "model");
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = validateYear(year);
    }

    public CarType getType() {
        return type;
    }

    public void changeType(CarType type) {
        this.type = Validator.requireNonNull(type, "Car type");
    }

    public CarStatus getStatus() {
        return status;
    }

    public String getCurrentRenterId() {
        return currentRenterId;
    }

    public boolean isAvailable() {
        return status == CarStatus.AVAILABLE;
    }

    public boolean isRented() {
        return status == CarStatus.RENTED;
    }

    public boolean isRemoved() {
        return status == CarStatus.REMOVED;
    }

    /**
     * Marks this car as rented by a customer
     *
     * @param renterId the id of the customer renting the car
     * @throws IllegalStateException if the car is removed or already rented
     * @throws IllegalArgumentException if renterId is null or blank
     */
    @Override
    public void rent(String renterId) {
        if (isRemoved()) {
            throw new IllegalStateException("Cannot rent a removed car.");
        }
        if (isRented()) {
            throw new IllegalStateException("Car is already rented.");
        }
        currentRenterId = Validator.requireNonBlank(renterId, "renterId");
        status = CarStatus.RENTED;
    }

    /**
     * Returns this car to the car fleet from active rental
     * @throws IllegalStateException if the car is not currently rented
     */
    @Override
    public void returnFromRental() {
        if (!isRented()) {
            throw new IllegalStateException("Car is not currently rented.");
        }
        currentRenterId = null;
        status = CarStatus.AVAILABLE;
    }

    /**
     * Removes this car from the active fleet.
     * The car cannot be removed while rented.
     *
     * @throws IllegalStateException if the car is currently rented
     */
    public void removeFromFleet() {
        if (isRented()) {
            throw new IllegalStateException("Cannot remove a rented car.");
        }
        status = CarStatus.REMOVED;
    }

    /**
     *
     * @param year the year in which the car was manufactured
     * @return returns the year if it is a valid one
     * @throws IllegalArgumentException if the year passed is invalid
     */
    private static int validateYear(int year) {
        if (year < 1900 || year > 2026) {
            throw new IllegalArgumentException("Year must be a valid car production year.");
        }
        return year;
    }

    /**
     *
     * @param customerId ID of the customer
     * @return null if the parameter is empty or null or the customer ID
     */
    private String validateCurrentRenterId(String customerId){
        if(customerId == null || customerId.trim().isEmpty()){
            return null;
        }
        return customerId.trim();
    }

    public static Car restoreCar(String id, String make, String model, int year,
                                 CarType type, CarStatus status, String currentRenterId) {
        return new Car(id,make,model, year, type, status, currentRenterId);
    }

    @Override
    public String toString() {
        return "\tID: " + id +
                "\tMake: " + make +
                "\tModel: " + model +
                "\tYear " + year +
                "\tBody type: " + type.getTypeName() +
                "\tStatus: " + status;
    }
}
