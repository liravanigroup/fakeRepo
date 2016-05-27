package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.exceptions.ProductNotAvailableException;
import pl.com.bottega.photostock.sales.model.users.Client;
import pl.com.bottega.photostock.sales.model.usertool.Reservation;

import java.util.*;


public class ReservationRepository implements Repository<Reservation> {

    private static Map<String, Reservation> reservationDataBase = new HashMap<>();

    @Override
    public void save(Reservation reservation) {
        if (reservation.getOwner() == null)
            throw new IllegalArgumentException("Forbidden! Try to save null reservation");
        reservationDataBase.put(reservation.getNumber(), reservation);
    }

    @Override
    public List<Reservation> find(String number) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Reservation findOpenedPer(Client client) {
        throw new UnsupportedOperationException();
    }

    public static Reservation findOpenPer(Client client) {
        for (Map.Entry<String, Reservation> entry : reservationDataBase.entrySet()) {
            if (entry.getValue().getOwner().equals(client) && !entry.getValue().isClosed())
                return entry.getValue();
        }
        return null;
    }

    @Override
    public Reservation load(String number) {
        Reservation result = reservationDataBase.get(number);
        if (result == null)
            throw new ProductNotAvailableException("Reservation was not create", number, ReservationRepository.class);
        return result;
    }
}
