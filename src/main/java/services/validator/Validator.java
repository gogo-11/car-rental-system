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

    public static CarType requireNonNull(CarType type, String fieldType) {
        if(type == null){
            throw new IllegalArgumentException(fieldType + " cannot be null");
        }

        return type;
    }

    public static String requireValidEmail(String email, String fieldName) {
        String value = requireNonBlank(email, fieldName);
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!value.matches(regex)) {
            throw new IllegalArgumentException(fieldName + " must be a valid email.");
        }
        return value;
    }
}
