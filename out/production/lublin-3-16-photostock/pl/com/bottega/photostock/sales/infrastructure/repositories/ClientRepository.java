package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.deal.Money;
import pl.com.bottega.photostock.sales.model.exceptions.ProductNotAvailableException;
import pl.com.bottega.photostock.sales.model.users.Client;
import pl.com.bottega.photostock.sales.model.usertool.Reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ClientRepository implements Repository<Client> {

    private static Map<String, Client> clientDataBase = new HashMap<>();

    @Override
    public Client load(String number) {
        Client result = clientDataBase.get(number);
        if (result == null)
            throw new ProductNotAvailableException("Client nr " + number + " does not exist", number, ClientRepository.class);
        return result;
    }

    @Override
    public void save(Client client) {
        if(client.getNumber() == null)
            client.setNumber(UUID.randomUUID().toString());
        clientDataBase.put(client.getNumber(), client);
    }


    public List<Client> find(String productName, String productNumber, Money minPrice, Money maxPrice, String... tags) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Client> find(String number) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Reservation findOpenedPer(Client client) {
        throw new UnsupportedOperationException();
    }
}
