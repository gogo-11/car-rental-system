package services.validator;

/**
 * Helper class with validation methods for Customer objects
 */
public class CustomerValidator {
    private CustomerValidator() {}

    /**
     * Checks if the email provided matches the regex for valid email
     * @param email the email string to be checked
     * @return true if the email is valid and false if it's not
     */
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * checks if the name provided matches the regex for valid name
     * @param name the name to be checked
     * @return true if the name matches the pattern and false if it doesn't
     */
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.matches("^[A-Za-z][A-Za-z\\\\s'-]{0,49}$");
    }

}
