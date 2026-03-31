package storage.reader;

import interfaces.storage.StorageReader;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Abstract class which serves as a base for all storage classes
 * @param <T> the type of objects to be read from the storage
 */
public abstract class AbstractStorageReader<T> implements StorageReader<T> {
    protected static final String COMMA_DELIMITER = ",";
    protected abstract String[] expectedColumns();

    /**
     * Reads and validates the first line of a file
     * The first line should contain the expected columns separated by commas
     *
     * @param bufferedReader the reader to read the file
     * @param fileName the name of the file to read
     * @throws IOException if an I/O error occurs while reading the file
     */
    protected void readAndValidateFirstLine(BufferedReader bufferedReader, String fileName) throws IOException {
        String firstLine = bufferedReader.readLine();

        String[] columns;
        if(firstLine == null || firstLine.trim().isEmpty()){
            columns = expectedColumns();
        } else {
            columns = firstLine.split(COMMA_DELIMITER,-1);
        }

        if(!areColumnsValid(columns)) {
            throw new IllegalArgumentException("Invalid first line in " + fileName + " CSV file. Expected: " +
                    String.join(",", expectedColumns()));
        }
    }

    /**
     * Checks if the columns in the first line of a file match the expected columns
     *
     * @param cols the columns to check
     * @return true if the columns are valid, false otherwise
     */
    protected boolean areColumnsValid(String[] cols){
        if(cols ==null || cols.length != expectedColumns().length){
            return false;
        }

        for (int i = 0; i < expectedColumns().length; i++) {
            String element;
            if(cols[i] == null){
                element = "";
            } else {
                element = cols[i].trim();
            }
            if(!expectedColumns()[i].equalsIgnoreCase(element)){
                return false;
            }
        }
        return true;
    }
}
