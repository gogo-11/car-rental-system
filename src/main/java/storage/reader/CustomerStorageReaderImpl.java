package storage.reader;

import entities.Customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomerStorageReaderImpl extends AbstractStorageReader<Customer> {
    private static final String[] EXPECTED_COLUMNS = {"Id","FirstName","LastName","Email"};
    private static final int COLUMN_NUMBER = EXPECTED_COLUMNS.length;

    /**
     * @param bufferedReader reader for reading the file
     * @return map of
     * @throws IOException
     */
    @Override
    public Map<String, Customer> readFile(BufferedReader bufferedReader) throws IOException{

        readAndValidateFirstLine(bufferedReader, "customer.csv");

        Map<String, Customer> customerMap = new HashMap<>();
        String line;

        while((line = bufferedReader.readLine()) != null) {
            if(!line.isBlank()){
                String[] values = line.split(COMMA_DELIMITER,-1);
                if(values.length == COLUMN_NUMBER){
                    String id = values[0].trim();
                    String firstName = values[1].trim();
                    String lastName = values[2].trim();
                    String email = values[3].trim().isEmpty() ? null : values[3].trim();

                    Customer customer = Customer.restoreCustomer(id, firstName, lastName, email);
                    if(customerMap.containsKey(id)){
                        throw new IllegalStateException("Customer with id " + id + " already exists");
                    }
                    customerMap.put(id, customer);
                } else {
                    throw new IllegalStateException("The read line is invalid" + line);
                }
            }
        }

        return customerMap;
    }

    /**
     * @return the expected columns constant
     */
    @Override
    protected String[] expectedColumns() {
        return EXPECTED_COLUMNS;
    }
}
