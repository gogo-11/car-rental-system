package app.console_input_output;

public class ConsoleIO {
    public static void handleHelp() {
        System.out.println("=========\tHELP MENU\t=========");
        System.out.println("Example valid commands:");
        System.out.println("Add Car");
        System.out.println("Add Customer");
        System.out.println("Rent Car");
        System.out.println("Return Car");
        System.out.println("Edit Car 14h5j6k");
        System.out.println("List Cars");
        System.out.println("List Available Cars");
        System.out.println("Search by model Q7");
        System.out.println("Search by status AVAILABLE");
        System.out.println("Search by ID 8jkl0ag");
        System.out.println("Remove 45d6f5g6");
        System.out.println("Help");
        System.out.println("Save and Exit");
    }

    public static void printMenu() {
        System.out.println("Commands:");
        System.out.println("Add Car");
        System.out.println("Add Customer");
        System.out.println("Rent Car");
        System.out.println("Return Car");
        System.out.println("Edit Car <car ID>");
        System.out.println("List Cars");
        System.out.println("List Available Cars");
        System.out.println("Search by model <type model here>");
        System.out.println("Search by status <car Status> (status can be AVAILABLE, RENTED)");
        System.out.println("Search by ID <car ID>");
        System.out.println("Remove <car ID>");
        System.out.println("Help");
        System.out.println("Save and Exit");
    }
}
