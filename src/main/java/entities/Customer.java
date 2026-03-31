package entities;

import interfaces.Identifiable;
import services.validator.Validator;

import java.util.UUID;

/**
 * Represents a customer in the rental system.
 * Stores customer details and manages rental-related operations
 */
public class Customer implements Identifiable {
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

    private Customer(String id, String firstName, String lastName, String email) {
        this.id = Validator.requireNonBlank(id, "Customer id cannot be empty");
        this.firstName = Validator.requireNonBlank(firstName, "Customer name cannot be empty");
        this.lastName = Validator.requireNonBlank(lastName, "Customer name cannot be empty");
        this.email = Validator.requireValidEmail(email, "Please, enter a valid email");
    }

    public static Customer restoreCustomer(String id, String firstName, String lastName, String email) {
        return new Customer(id,firstName,lastName,email);
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }


}
