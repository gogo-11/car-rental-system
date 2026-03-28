package services.validator;

import java.time.LocalDate;

public class RentalValidator {
    private RentalValidator() {}


    public static String requireExistingId(String id, String fieldName) {
        if(id == null || id.trim().isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or blank");
        }
        return id.trim();
    }

    public static LocalDate requireValidReturnDate(LocalDate rentedOn, LocalDate expectedReturnDate, String message) {
        if(expectedReturnDate.isBefore(rentedOn)) {
            throw new IllegalArgumentException(message);
        }
        return expectedReturnDate;
    }
}
