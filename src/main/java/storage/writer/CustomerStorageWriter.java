package storage.writer;

import entities.Customer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

public class CustomerStorageWriter extends AbstractStorageWriter<Customer> {
    private static final String COLUMNS = "Id,FirstName,LastName,Email";
    /**
     * @return the columns string
     */
    @Override
    protected String getColumns() {
        return COLUMNS;
    }

    /**
     * Writes the content of the provided map of customers to the customers.csv file
     *
     * @param writer the writer to handle the write operation
     * @param customers the map of customers to write
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void writeFile(BufferedWriter writer, Map<String, Customer> customers) throws IOException {
        writeFirstLine(writer);

        for (Customer customer : customers.values()){
            String customerRow = String.join(",", customer.getId(), customer.getFirstName(),
                    customer.getLastName(), customer.getEmail());

            writer.write(customerRow);
            writer.newLine();
        }

    }
}
