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
}
