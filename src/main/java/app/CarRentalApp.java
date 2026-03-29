package app;

import app.console_input_output.ConsoleIO;
import app.handler.CommandHandler;
import exceptions.ExceptionHandler;
import interfaces.CarRentalService;
import services.CarRentalServiceImpl;

import java.util.Scanner;

public class CarRentalApp {
    private CarRentalService service = new CarRentalServiceImpl();
    private Scanner scanner = new Scanner(System.in);
    private ExceptionHandler exceptionHandler = new ExceptionHandler();
    CommandHandler commandHandler = new CommandHandler(service, scanner);

    public static void main(String[] args) {

//        CarFileReader carFileReader = new CarFileReader("cars.csv");
//        CarFileWriter carFileWriter = new CarFileWriter("cars.csv");
        new CarRentalApp().startApp();

    }

    private void startApp() {
        System.out.println("Welcome to Car Rental System");
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
}
