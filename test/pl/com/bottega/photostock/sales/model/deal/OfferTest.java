package pl.com.bottega.photostock.sales.model.deal;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.com.bottega.photostock.sales.model.products.Picture;
import pl.com.bottega.photostock.sales.model.products.Product;
import pl.com.bottega.photostock.sales.model.users.Client;
import pl.com.bottega.photostock.sales.model.usertool.Reservation;

import static pl.com.bottega.photostock.sales.model.deal.Money.FIVE_PL;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-25.
 */

public class OfferTest {

    private static Client client = new Client("Ivan", "Ivanov", "Lublin", Money.ZERO_PL);
    private static Reservation reservation = new Reservation(client);
    private static Offer offer_1;
    private static Offer offer_2;

    @BeforeClass
    public static void shouldInitialStartStain() {
        Product product_0 = new Picture("Name", "", FIVE_PL, true, "Tag1", "Tag2", "Tag3");
        Product product_1 = new Picture("Name", "", FIVE_PL, true, "Tag1", "Tag2", "Tag3");
        Product product_2 = new Picture("Name", "", FIVE_PL, true, "Tag1", "Tag2", "Tag3");
        Product product_3 = new Picture("Name", "", FIVE_PL, true, "Tag1", "Tag2", "Tag3");

        reservation.add(product_0);
        reservation.add(product_1);
        reservation.add(product_2);
        reservation.add(product_3);

        offer_1 = reservation.generateOffer();
        offer_2 = reservation.generateOffer();
    }

    @Test
    public void sameAs() {
        Assert.assertTrue(offer_1.sameAs(offer_2,2));
    }

    @Test
    public void shouldGetProducts() {
        Assert.assertEquals(offer_1.getItems().size(), 4);
    }

    @Test
    public void shouldGetTotalCost() {
        Assert.assertEquals(offer_1.getTotalCost(), new Money(20));
    }

    @Test
    public void getItemsCount() {
        Assert.assertEquals(offer_1.getItemsCount(), 4);
    }
}