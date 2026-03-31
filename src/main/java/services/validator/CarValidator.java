package services.validator;

/**
 * Helper class with validation methods for Car objects
 */
public class CarValidator {
    private CarValidator() {}

    /**
     * checks if the car make provided matches the regex for valid make
     * @param make the make name to be checked
     * @return true if the make matches the pattern and false if it doesn't
     */
    public static boolean isValidName(String make) {
        return make != null && make.matches("^[A-Za-z]+$");
    }
}
