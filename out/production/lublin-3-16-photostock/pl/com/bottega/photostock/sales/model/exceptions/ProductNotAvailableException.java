package pl.com.bottega.photostock.sales.model.exceptions;

/**
 * Created by Admin on 16.04.2016.
 */
public class ProductNotAvailableException extends RuntimeException {

    private String number;
    private Class clazz;

    /**
     *
     * @param message
     * @param number bussines number
     * @param clazz type of throw
     */
    public ProductNotAvailableException(String message, String number, Class clazz) {
        super(message);
        this.number = number;
        this.clazz = clazz;
    }

    public Class getClazz(){
        return clazz;
    }

    public String getNumber() {
        return number;
    }
}
