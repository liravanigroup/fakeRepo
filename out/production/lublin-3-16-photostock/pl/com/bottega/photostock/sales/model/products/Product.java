package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.deal.Money;
import pl.com.bottega.photostock.sales.model.users.Client;

/**
 * Created on 03.04.2016.
 */
public interface Product {
    Money calculatePrice();

    String[] getTags();
    String getName();
    String getNumber();
    String[] export();

    boolean isAvailable();

    void setNumber(String number);

    void reservePer(Client client);
    void unReservePer(Client client);

    void deactivate();
    void activate();
}
