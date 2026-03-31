package exceptions;

public class ExceptionHandler {
    public void handleException(Exception e){
        if (e instanceof IllegalArgumentException) {
            System.out.println("Invalid input: " + e.getMessage());
        } else if (e instanceof IllegalStateException) {
            System.out.println("Operation not allowed: " + e.getMessage());
        } else if (e instanceof java.util.NoSuchElementException) {
            System.out.println("Not found: " + e.getMessage());
        } else if (e instanceof java.time.DateTimeException) {
            System.out.println("Invalid date: " + e.getMessage());
        } else {
            System.out.println("Unexpected error: " + e.getMessage() + e);
        }
    }
}
