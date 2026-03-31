package storage.writer;

import entities.RentStatus;
import entities.Rental;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

public class RentalStorageWriter extends AbstractStorageWriter<Rental> {
    private static final String COLUMNS = "Id,CarId,CustomerId,RentedOn,ExpectedReturnDate,ActualReturnDate,Status";

    /**
     * @param writer the writer to handle the write operation
     * @param rentals the map of customers to write
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void writeFile(BufferedWriter writer, Map<String, Rental> rentals) throws IOException {
        writeFirstLine(writer);

        for (Rental rental : rentals.values()){
            String actualReturn = rental.getActualReturnDate() == null ? "" : rental.getActualReturnDate().toString();
            String rentalRow = String.join(",",
                    rental.getId(),
                    rental.getCarId(),
                    rental.getCustomerId(),
                    rental.getRentedOn().toString(),
                    rental.getExpectedReturnDate().toString(),
                    actualReturn,
                    getStatus(rental.isActive()).name());

            writer.write(rentalRow);
            writer.newLine();
        }
    }

    /**
     * @return the columns string
     */
    @Override
    protected String getColumns() {
        return COLUMNS;
    }

    private RentStatus getStatus(boolean isActive){
        return isActive ? RentStatus.ACTIVE : RentStatus.COMPLETED;
    }
}
