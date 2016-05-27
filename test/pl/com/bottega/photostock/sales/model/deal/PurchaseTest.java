package pl.com.bottega.photostock.sales.model.deal;

import org.junit.Assert;
import org.junit.Test;
import pl.com.bottega.photostock.sales.model.users.Client;

import java.util.ArrayList;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-25.
 */

public class PurchaseTest {
    private static Client owner = new Client("Ivan","Abramov","Lublin", Money.ZERO_PL);
    private static Purchase p1 = new Purchase(owner, new ArrayList<>(10));
    private static Purchase p2 = new Purchase(owner, new ArrayList<>(10));

    @Test
    public void getNumber() throws Exception {

    }

    @Test
    public void getOwner() throws Exception {

    }

    @Test
    public void getPurchaseData() throws Exception {

    }

    @Test
    public void getProducts() throws Exception {

    }

    @Test
    public void getTotalCost() throws Exception {

    }

    @Test
    public void shouldEqualsTrue() {
        Assert.assertEquals(p1,p2);
    }

    @Test
    public void shouldGenerateTheSameHashCodes() throws Exception {
        Assert.assertEquals(p1.hashCode(),p2.hashCode());
    }

}