package entities;

import interfaces.Identifiable;
import services.validator.RentalValidator;
import services.validator.Validator;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a rental in the rental system
 * Stores rental details and manages rental-related data and operations
 */
public class Rental implements Identifiable {
    private final String id;
    private final String carId;
    private final String customerId;
    private final LocalDate rentedOn;
    private final LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;
    private RentStatus status;

    public Rental(String carId,
                  String customerId,
                  LocalDate expectedReturnDate) {
        this.id = UUID.randomUUID().toString();
        this.carId = Validator.requireNonBlank(carId, "Car ID");
        this.customerId = Validator.requireNonBlank(customerId, "Customer ID");
        this.rentedOn = LocalDate.now();
        this.expectedReturnDate = RentalValidator.requireValidReturnDate(rentedOn, expectedReturnDate, "Expected return date cannot be before the date rented on");
        this.actualReturnDate = null;
        this.status = RentStatus.ACTIVE;
    }

    public Rental(String id, String carId, String customerId, LocalDate rentedOn, LocalDate expectedReturnDate, LocalDate actualReturnDate, RentStatus status) {
        this.id = Validator.requireNonBlank(id, "rental ID");
        this.carId = RentalValidator.requireNonNullOrBlankId(carId, "Car ID");
        this.customerId = RentalValidator.requireNonNullOrBlankId(customerId, "Customer ID");
        this.rentedOn = rentedOn;
        this.expectedReturnDate = RentalValidator.requireValidReturnDate(rentedOn, expectedReturnDate, "Expected return date cannot be before the date rented on");
        this.actualReturnDate = actualReturnDate == null ?
                null : RentalValidator.requireValidReturnDate(rentedOn, actualReturnDate, "Expected return date cannot be before the date rented on");
        this.status = status;
    }

    public static Rental restoreRental(String id, String carId, String customerId, LocalDate rentedOn, LocalDate expectedReturnDate, LocalDate actualReturnDate, RentStatus status) {
        return new Rental(id, carId,customerId, rentedOn, expectedReturnDate, actualReturnDate, status);
    }

    @Override
    public String getId() {
        return id;
    }

    public String getCarId() {
        return carId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public LocalDate getRentedOn() {
        return rentedOn;
    }

    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public boolean isActive() {
        return status.equals(RentStatus.ACTIVE);
    }

    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = RentalValidator.requireValidReturnDate(
                rentedOn,
                actualReturnDate,
                "Expected return date cannot be before the date rented on");
    }

    public void markAsCompleted(LocalDate actualReturnDate) {
        setActualReturnDate(actualReturnDate);
        if(!status.equals(RentStatus.COMPLETED)){
            status = RentStatus.COMPLETED;
        } else {
            throw new IllegalStateException("Rental already completed");
        }
        setActualReturnDate(actualReturnDate);
    }
}
