package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.exceptions.ProductNotAvailableException;
import pl.com.bottega.photostock.sales.model.users.Client;
import pl.com.bottega.photostock.sales.model.usertool.LightBox;
import pl.com.bottega.photostock.sales.model.usertool.Reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LightBoxRepository implements Repository<LightBox> {
    private static Map<String, LightBox> lightBoxDataBase = new HashMap<>();

    @Override
    public LightBox load(String number) {
        LightBox result = lightBoxDataBase.get(number);
        if (result == null)
            throw new ProductNotAvailableException("LightBox nr " + number + " does not exist", number, LightBoxRepository.class);
        return result;
    }

    @Override
    public void save(LightBox lightBox) {
        if (lightBox == null)
            throw new IllegalArgumentException("Save error!");
        lightBoxDataBase.put(lightBox.getNumber(), lightBox);
    }

    @Override
    public List<LightBox> find(String number) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Reservation findOpenedPer(Client client) {
        throw new UnsupportedOperationException();
    }
}
