package services.validator;

import entities.CarType;

public class CarValidator {
    private CarValidator() {}

    public static boolean isValidName(String make) {
        return make != null && make.matches("^[A-Za-z]+$");
    }

    public static boolean isValidType(String typeToCheck) {
        try {
            CarType parsedType = CarType.valueOf(typeToCheck.trim().toUpperCase().replace(' ', '_'));
        } catch (IllegalArgumentException ex) {
            return false;
        }
        return true;
    }
}
