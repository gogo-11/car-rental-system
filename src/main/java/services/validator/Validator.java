package services.validator;

import entities.CarType;

public class Validator {
    private Validator(){}
    public static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank.");
        }
        return value.trim();
    }

    public static CarType requireNonNull(CarType type, String message) {
        if(type == null){
            throw new IllegalArgumentException(message);
        }

        return type;
    }

    public static String requireValidEmail(String email, String fieldName) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException(fieldName + " must be a valid email.");
        }
        return email;
    }

}
