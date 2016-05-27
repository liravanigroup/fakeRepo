package pl.com.bottega.photostock.sales.model.exceptions;

/**
 * Sergej Povzaniuk
 * 2016-04-21.
 */
public class DataDoesNotExistsException extends RuntimeException {

    private String number;
    private Class clazz;

    public DataDoesNotExistsException(String message, String number, Class clazz) {
        super(message);
        this.number = number;
        this.clazz = clazz;
    }

    public String getNumber() {
        return number;
    }

    public Class getClazz() {
        return clazz;
    }

}
