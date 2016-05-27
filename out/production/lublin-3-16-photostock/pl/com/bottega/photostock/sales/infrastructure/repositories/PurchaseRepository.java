package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.deal.Purchase;
import pl.com.bottega.photostock.sales.model.exceptions.ProductNotAvailableException;
import pl.com.bottega.photostock.sales.model.users.Client;
import pl.com.bottega.photostock.sales.model.usertool.Reservation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 17.04.2016.
 */
public class PurchaseRepository implements Repository<Purchase> {

    private static Map<String, Purchase> purchaseDataBase = new HashMap<>();

    @Override
    public Purchase load(String purchaseNumber) {
        Purchase result = purchaseDataBase.get(purchaseNumber);
        if (result == null)
            throw new ProductNotAvailableException("Purchase nr " + purchaseNumber + "does not exist", purchaseNumber, PurchaseRepository.class);
        return result;
    }


    public List<Purchase> find(String clientNumber) {
        List<Purchase> result = new LinkedList<>();

        for (Map.Entry<String, Purchase> entry : purchaseDataBase.entrySet()) {
            if (entry.getValue().getOwner().getNumber().equals(clientNumber))
                result.add(entry.getValue());
        }
        if (result.size() > 0)
            return result;
        else
            throw new IllegalArgumentException("Reservation is absent");
    }

    @Override
    public Reservation findOpenedPer(Client client) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(Purchase purchase) {
        purchaseDataBase.put(purchase.getNumber(), purchase);
    }
}
