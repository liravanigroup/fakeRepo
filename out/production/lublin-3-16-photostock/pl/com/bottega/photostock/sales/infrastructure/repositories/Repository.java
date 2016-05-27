package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.users.Client;
import pl.com.bottega.photostock.sales.model.usertool.Reservation;

import java.util.List;

public interface Repository<T> {
    T load(String number);

    void save(T product);

    List<T> find(String number);

    Reservation findOpenedPer(Client client);
}
