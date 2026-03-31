package storage.reader;

import entities.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Used for reading the rentals storage file
 */
public class RentalStorageReader extends AbstractStorageReader<Rental> {
    private static final String[] EXPECTED_COLUMNS = {
            "Id","CarId","CustomerId","RentedOn",
            "ExpectedReturnDate","ActualReturnDate","Status"};
    private static final int COLUMN_NUMBER = EXPECTED_COLUMNS.length;

    /**
     * @param bufferedReader reader for reading the file
     * @return map of all rentals
     * @throws IOException if an I/O error occurs while reading the file
     */
    @Override
    public Map<String, Rental> readFile(BufferedReader bufferedReader) throws IOException {
        readAndValidateFirstLine(bufferedReader, "rentals.csv");
        Map<String, Rental> rentalsMap = new HashMap<>();
        String line;

        while((line = bufferedReader.readLine()) != null) {
            if(!line.isBlank()){
                String[] values = line.split(COMMA_DELIMITER,-1);
                if(values.length == COLUMN_NUMBER){

                    String id = values[0].trim();
                    String carId = values[1].trim();
                    String customerId = values[2].trim();
                    LocalDate rentedOn = LocalDate.parse(values[3].trim());
                    LocalDate expectedReturnDate = LocalDate.parse(values[4].trim());
                    LocalDate actualReturnDate = values[5].trim().isEmpty() ? null : LocalDate.parse(values[5].trim());
                    RentStatus status = RentStatus.valueOf(values[6].trim().toUpperCase());

                    Rental rental = Rental.restoreRental(id, carId, customerId, rentedOn, expectedReturnDate, actualReturnDate, status);
                    if(rentalsMap.containsKey(id)){
                        throw new IllegalStateException("Rental with id " + id + " already exists");
                    }
                    if (status == RentStatus.COMPLETED && actualReturnDate == null) {
                        throw new IllegalStateException("Completed rental must have actual return date. " + line);
                    }
                    if (status == RentStatus.ACTIVE && actualReturnDate != null) {
                        throw new IllegalStateException("Active rental cannot have actual return date. " + line);
                    }
                    rentalsMap.put(id, rental);
                } else {
                    throw new IllegalStateException("The read line is invalid" + line);
                }
            }
        }
        return rentalsMap;
    }

    /**
     * @return the expected columns constant
     */
    @Override
    protected String[] expectedColumns() {
        return EXPECTED_COLUMNS;
    }
}
