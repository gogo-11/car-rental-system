package entities;

import java.util.Objects;

public class Car {
    private final String id;
    private String make;
    private String model;
    private int year;
    private final CarType type;
    private CarStatus status;
    private String currentRenterName;

    public Car(String id, String make, String model, int year, CarType type) {
        this.id = requireNonBlank(id, "id");
        this.make = requireNonBlank(make, "make");
        this.model = requireNonBlank(model, "model");
        this.year = validateYear(year);
        this.type = Objects.requireNonNull(type, "type cannot be null");
        this.status = CarStatus.AVAILABLE;
    }

    public String getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = requireNonBlank(make, "make");
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = requireNonBlank(model, "model");
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

    public CarStatus getStatus() {
        return status;
    }

    public String getCurrentRenterName() {
        return currentRenterName;
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

    public void rentTo(String renterName) {
        if (isRemoved()) {
            throw new IllegalStateException("Cannot rent a removed car.");
        }
        if (isRented()) {
            throw new IllegalStateException("Car is already rented.");
        }
        currentRenterName = requireNonBlank(renterName, "renterName");
        status = CarStatus.RENTED;
    }

    public void returnFromRental() {
        if (!isRented()) {
            throw new IllegalStateException("Car is not currently rented.");
        }
        currentRenterName = null;
        status = CarStatus.AVAILABLE;
    }

    public void removeFromFleet() {
        if (isRented()) {
            throw new IllegalStateException("Cannot remove a rented car.");
        }
        status = CarStatus.REMOVED;
    }

    private static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank.");
        }
        return value.trim();
    }

}
