package pl.com.bottega.photostock.sales.model.usertool;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.com.bottega.photostock.sales.model.deal.Money;
import pl.com.bottega.photostock.sales.model.products.Product;
import pl.com.bottega.photostock.sales.model.products.ProductFactory;
import pl.com.bottega.photostock.sales.model.users.Client;

import static pl.com.bottega.photostock.sales.model.products.AbstractProduct.ProductType.PICTURE;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-11.
 */

public class ReservationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    Client client = new Client("Ivan", "Ivanov", "Lublin", Money.ZERO_PL);
    Reservation reservation = new Reservation(client);
    Product product = ProductFactory.getProductInstance(PICTURE, "Car", "nr234", new Money(10), true, "car", "auto", "jeep");

    @Test
    public void testIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("");
        Reservation reservation = new Reservation(null);
    }

    @Test
    public void shouldCreateReservation() {
        Reservation reservation = new Reservation(client);
        Assert.assertNotEquals(reservation, null);
    }

    @Test
    public void add() throws Exception {
        reservation.add(product);
        Assert.assertEquals(1, reservation.getItemsCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddIllegalArgumentException() {
        reservation.add(product);
        reservation.add(product);
    }

    @Test
    public void remove() throws Exception {
        reservation.add(product);
        reservation.remove(product);
        Assert.assertEquals(0, reservation.getItemsCount());
    }

    @Test
    public void generateOffer() throws Exception {
        reservation.add(product);
        reservation.generateOffer();
    }

    @Test
    public void getItemsCount() throws Exception {
        reservation.add(product);
        Assert.assertEquals(1, reservation.getItemsCount());
    }

}