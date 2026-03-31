package storage.reader;

import interfaces.storage.StorageReader;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class AbstractStorageReader<T> implements StorageReader<T> {
    protected static final String COMMA_DELIMITER = ",";
    protected abstract String[] expectedColumns();

    protected void readAndValidateFirstLine(BufferedReader bufferedReader, String fileName) throws IOException {
        String firstLine = bufferedReader.readLine();
//        if (firstLine == null) {
//            throw new IllegalArgumentException("CSV file is empty or missing first line.");
//        }

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
