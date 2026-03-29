package entities;

import services.validator.RentalValidator;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a rental in the rental system
 * Stores rental details and manages rental-related data and operations
 */
public class Rental {
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
        this.carId = RentalValidator.requireExistingId(carId, "Car ID");
        this.customerId = RentalValidator.requireExistingId(customerId, "Customer ID");
        this.rentedOn = LocalDate.now();
        this.expectedReturnDate = RentalValidator.requireValidReturnDate(rentedOn, expectedReturnDate, "Expected return date cannot be before the date rented on");
        this.actualReturnDate = null;
        this.status = RentStatus.ACTIVE;
    }

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
        if(!status.equals(RentStatus.COMPLETED)){
            status = RentStatus.COMPLETED;
        } else {
            throw new IllegalStateException("Rental already completed");
        }
        setActualReturnDate(actualReturnDate);
    }
}
