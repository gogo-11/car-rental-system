package app.handler;

import app.console_input_output.ConsoleIO;
import entities.Car;
import entities.CarStatus;
import entities.CarType;
import entities.Rental;
import interfaces.CarRentalService;
import services.CarRentalServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CommandHandler {
    private CarRentalService service;
    private Scanner scanner;

    public CommandHandler(CarRentalService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public boolean handleCommand(String input) {
        String command = input.trim();

        if (command.equalsIgnoreCase("save and exit")) {
            return handleSaveAndExit();
        } else if(command.equalsIgnoreCase("help")){
            ConsoleIO.handleHelp();
        } else if(command.equalsIgnoreCase("add car")){
            handleAddCar();
        } else if(command.equalsIgnoreCase("rent car")){
            handleRentCar();
        } else if(command.equalsIgnoreCase("return car")){
            handleReturnCar();
        } else if(command.startsWith("Edit Car")){
            String carId = command.substring("Edit Car ".length()).trim();
            if(carId.isBlank())
                throw  new IllegalArgumentException("Missing car ID");
            handleEditCar(carId);
        } else if (command.equalsIgnoreCase("list cars")) {
            handleListCars();
        } else if(command.equalsIgnoreCase("list available cars")){
            handleListAvailableCars();
        } else if(command.startsWith("Search by model")){
            String model = command.substring("Search by model ".length()).trim();
            if(model.isBlank())
                throw  new IllegalArgumentException("Missing car model");
            handleSearchByModel(model);
        } else if(command.startsWith("Search by status")){
            String status = command.substring("Search by status ".length()).trim();
            if(status.isBlank())
                throw  new IllegalArgumentException("Missing car status");
            handleSearchByStatus(status);
        } else if(command.startsWith("Search by ID")){
            String carId = command.substring("Search by ID ".length()).trim();
            if(carId.isBlank())
                throw  new IllegalArgumentException("Missing car ID");
            handleSearchById(carId);
        } else if(command.startsWith("Remove")) {
            String carId = command.substring("Remove ".length()).trim();
            if(carId.isBlank())
                throw  new IllegalArgumentException("Missing car ID");
            handleRemoveCar(carId);
        } else {
            System.out.println("Unknown command. Type \"Help\" to read more.");
        }

        return true;
    }

    private void handleAddCar() {
        System.out.print("Enter car make: ");
        String make = scanner.nextLine();
        System.out.println("Enter car model: ");
        String model = scanner.nextLine();
        int year = 0;
        while(true){
            System.out.println("Enter year of manufacturing (between 1900-2026): ");
            try {
                year = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again!");
            }
        }

        System.out.println("===Available types===\n" +
                "SEDAN, SUV, HATCHBACK, WAGON, PICKUP, COUPE, CONVERTIBLE" +
                "\nEnter car type:");
        String carType = scanner.nextLine();

        service.addCar(make, model,year, parseCarType(carType));
        System.out.println("Car added successfully.");
    }

    private void handleRentCar() {
        System.out.println("Enter the car ID you want to rent: ");
        String carId = scanner.nextLine();
        System.out.println("Enter your personal customer ID: ");
        String customerId = scanner.nextLine();
        System.out.println("Enter expected return date (format: YYYY-MM-DD");
        String date = scanner.nextLine();


        Rental rental = service.rentCar(carId, customerId, LocalDate.parse(date));
        System.out.println("Car rented. Rental ID: " + rental.getId());
    }

    private void handleReturnCar() {
        System.out.println("Enter the car ID: ");
        String carId = scanner.nextLine();
        System.out.println("Enter expected return date (format: YYYY-MM-DD");
        String date = scanner.nextLine();

        Rental rental = service.returnCar(carId, LocalDate.parse(date));
        System.out.println("Car returned: " + rental.getId());
    }

    private void handleEditCar(String carId) {
        System.out.print("Enter car make: ");
        String make = scanner.nextLine();
        System.out.println("Enter car model: ");
        String model = scanner.nextLine();
        int year = 0;
        while(true){
            System.out.println("Enter year of manufacturing (between 1900-2026): ");
            try {
                year = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again!");
            }
        }

        System.out.println("===Available types===\n" +
                "SEDAN, SUV, HATCHBACK, WAGON, PICKUP, COUPE, CONVERTIBLE" +
                "\nEnter car type:");
        String carType = scanner.nextLine();

        service.editCar(carId, make, model, year, parseCarType(carType));
        System.out.println("Car " + make + " " + model + "edited.");
    }

    private void handleListCars() {
        List<Car> carsList = service.getAllCars();
        System.out.printf("Found %d cars:", carsList.size());
        carsList.forEach(System.out::println);
    }

    private void handleListAvailableCars() {
        List<Car> carsList = service.getAvailableCars();
        System.out.printf("Found %d cars:", carsList.size());
        carsList.forEach(System.out::println);
    }

    private void handleSearchByModel(String model){
        List<Car> carsList = service.findCarsByModel(model);
        System.out.printf("Found %d cars:", carsList.size());
        carsList.forEach(System.out::println);
    }

    private void handleSearchByStatus(String status) {
        List<Car> carsList = service.findCarsByStatus(parseCarStatus(status));
        System.out.printf("Found %d cars:", carsList.size());
        carsList.forEach(System.out::println);
    }

    private void handleSearchById(String id) {
        Car carById = service.findCarById(id);
        System.out.println("Found car:\n" + carById);
    }

    private void handleRemoveCar(String carId) {
        service.removeCar(carId);
        System.out.println("Car removed.");
    }

    private boolean handleSaveAndExit() {
        // TODO
        return false;
    }

    private CarType parseCarType(String input) {
        return CarType.valueOf(input.trim().toUpperCase());
    }

    private CarStatus parseCarStatus(String input){
        return CarStatus.valueOf(input.trim().toUpperCase());
    }
}
