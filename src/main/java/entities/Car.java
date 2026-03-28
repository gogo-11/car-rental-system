package entities;

import interfaces.Rentable;
import services.validator.Validator;

import java.util.UUID;

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

    @Override
    public void returnFromRental() {
        if (!isRented()) {
            throw new IllegalStateException("Car is not currently rented.");
        }
        currentRenterId = null;
        status = CarStatus.AVAILABLE;
    }

    public void removeFromFleet() {
        if (isRented()) {
            throw new IllegalStateException("Cannot remove a rented car.");
        }
        status = CarStatus.REMOVED;
    }

    private static int validateYear(int year) {
        if (year < 1900) {
            throw new IllegalArgumentException("Year must be a valid car production year.");
        }
        return year;
    }
}
