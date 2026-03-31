package app;

import app.console_input_output.ConsoleIO;
import app.handler.CommandHandler;
import entities.Car;
import entities.Customer;
import entities.Rental;
import exceptions.ExceptionHandler;
import interfaces.service.CarRentalService;
import interfaces.storage.StorageReader;
import services.CarRentalServiceImpl;
import storage.reader.CarStorageReader;
import storage.reader.CustomerStorageReader;
import storage.reader.RentalStorageReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class CarRentalApp {
    private final CarRentalService service = new CarRentalServiceImpl();
    private final Scanner scanner = new Scanner(System.in);
    private final ExceptionHandler exceptionHandler = new ExceptionHandler();
    private final CommandHandler commandHandler = new CommandHandler(service, scanner);
    private final StorageReader<Car> carStorageReader = new CarStorageReader();
    private final StorageReader<Customer> customerStorageReader = new CustomerStorageReader();
    private final StorageReader<Rental> rentalStorageReader = new RentalStorageReader();
    private static final String CARS_CSV_PATH = "src/main/resources/database/cars.csv";
    private static final String CUSTOMERS_CSV_PATH = "src/main/resources/database/customers.csv";
    private static final String RENTALS_CSV_PATH = "src/main/resources/database/rentals.csv";

    public static void main(String[] args) {

        new CarRentalApp().startApp();

    }

    private void startApp() {
        loadCarsFromCsv();
        loadCustomersFromCsv();
        loadRentalsFromCsv();
        System.out.println("Welcome to Car Rental System\n");
        ConsoleIO.printMenu();

        boolean running = true;
        while (true) {
            System.out.print("Choose an action: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }

            try {
                running = commandHandler.handleCommand(input);
            } catch (Exception e) {
                exceptionHandler.handleException(e);
            }
            if(!running) {
                return;
            }
        }
    }

    /**
     * Populates the customer map in service
     */
    private void loadCustomersFromCsv() {
        try(BufferedReader customerReader = new BufferedReader(new FileReader(CUSTOMERS_CSV_PATH))) {
            Map<String, Customer> loadedCustomers = customerStorageReader.readFile(customerReader);
            service.loadCustomers(loadedCustomers);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load customers from customers.csv file: " + CUSTOMERS_CSV_PATH, e);
        }
    }

    /**
     * Populates the cars map in service
     */
    private void loadCarsFromCsv() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CARS_CSV_PATH))) {
            Map<String, Car> loadedCars = carStorageReader.readFile(reader);
            service.loadCars(loadedCars);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load cars from cars.csv file: " + CARS_CSV_PATH, e);
        }
    }

    /**
     * Populates the rentals map in service
     */
    private void loadRentalsFromCsv() {
        try(BufferedReader rentalsReader = new BufferedReader(new FileReader(RENTALS_CSV_PATH))) {
            Map<String, Rental> loadedRentals = rentalStorageReader.readFile(rentalsReader);
            service.loadRentals(loadedRentals);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load rentals from rentals.csv file: " + RENTALS_CSV_PATH, e);
        }
    }
}
