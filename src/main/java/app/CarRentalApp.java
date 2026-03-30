package app;

import app.console_input_output.ConsoleIO;
import app.handler.CommandHandler;
import entities.Car;
import exceptions.ExceptionHandler;
import interfaces.CarRentalService;
import interfaces.CarStorageReader;
import interfaces.CarStorageWriter;
import services.CarRentalServiceImpl;
import storage.CarStorageReaderImpl;

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
    private final CarStorageReader carStorageReader = new CarStorageReaderImpl();
    private static final String CARS_CSV_PATH = "src/main/resources/database/cars.csv";

    public static void main(String[] args) {

        new CarRentalApp().startApp();

    }

    private void startApp() {
        loadCarsFromCsv();
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

    private void loadCarsFromCsv() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CARS_CSV_PATH))) {
            Map<String, Car> loadedCars = carStorageReader.readCarsFile(reader);
            service.loadCars(loadedCars);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load cars from cars.csv file: " + CARS_CSV_PATH, e);
        }
    }
}
