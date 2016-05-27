package pl.com.bottega.photostock.sales.model.exceptions;

/**
 * Created by Robert Pitucha on 15.05.2016.
 */
public class DataAccessException extends RuntimeException{
    public DataAccessException(Exception e, String errorMessage) {
        super(errorMessage, e);
    }
}
