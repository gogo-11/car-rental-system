package services.validator;

import entities.CarType;

/**
 * Helper class with validation methods
 */
public class Validator {
    private Validator(){}

    /**
     * Checks if the provided parameter is not null or empty
     * @param value the value to be checked
     * @param fieldName the name of the field being checked
     * @return the trimmed value
     * @throws IllegalArgumentException if the value is null or blank
     */
    public static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.trim().isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank.");
        }
        return value.trim();
    }

    /**
     * Checks if the provided parameter is not null
     * @param type the value to be checked
     * @param fieldName the name of the field being checked
     * @return the value
     * @throws IllegalArgumentException if the value is null
     */
    public static CarType requireNonNull(CarType type, String fieldName) {
        if(type == null){
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }

        return type;
    }

    /**
     * Checks if the email provided is valid against a regex pattern
     * @param email the email to be checked
     * @param fieldName the name of the field being checked
     * @return the email if it is valid
     * @throws IllegalArgumentException if the email is invalid
     */
    public static String requireValidEmail(String email, String fieldName) {
        String value = requireNonBlank(email, fieldName);
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!value.matches(regex)) {
            throw new IllegalArgumentException(fieldName + " must be a valid email.");
        }
        return value;
    }
}
