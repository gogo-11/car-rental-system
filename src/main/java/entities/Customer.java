package entities;

import services.validator.Validator;

import java.util.UUID;

/**
 * Represents a customer in the rental system.
 * Stores customer details and manages rental-related operations
 */
public class Customer {
    private String id;
    private String firstName;
    private String lastName;
    private String email;

    public Customer(String firstName, String lastName, String email) {
        this.id = UUID.randomUUID().toString();
        this.firstName = Validator.requireNonBlank(firstName, "Customer name cannot be empty");
        this.lastName = Validator.requireNonBlank(lastName, "Customer name cannot be empty");
        this.email = Validator.requireValidEmail(email, "Please, enter a valid email");
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }


}
