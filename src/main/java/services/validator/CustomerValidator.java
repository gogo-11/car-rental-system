package services.validator;

public class CustomerValidator {
    private CustomerValidator() {}
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isValidName(String name) {
        return name != null && name.matches("^[A-Za-z]+$");
    }

}
