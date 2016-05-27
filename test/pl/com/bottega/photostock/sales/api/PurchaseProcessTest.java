package pl.com.bottega.photostock.sales.api;

import org.junit.Assert;
import org.junit.Test;
import pl.com.bottega.photostock.sales.infrastructure.repositories.*;
import pl.com.bottega.photostock.sales.model.deal.Money;
import pl.com.bottega.photostock.sales.model.products.Product;
import pl.com.bottega.photostock.sales.model.products.ProductFactory;
import pl.com.bottega.photostock.sales.model.users.Client;

import static pl.com.bottega.photostock.sales.model.products.AbstractProduct.ProductType.PICTURE;

public class PurchaseProcessTest {
    Repository clientRepository = new ClientRepository();
    Repository reservationRepository = new ReservationRepository();
    Repository purchaseRepository = new PurchaseRepository();
    Repository productRepository = new ProductRepository();
    Product pic = ProductFactory.getProductInstance(PICTURE, "name", "", new Money(15), true, "Tags");
    Client client = new Client("Jan", "Kowalski", "Lublin", Money.ZERO_PL);

    {
        clientRepository.save(client);
        productRepository.save(pic);
    }

    @Test
    public void shouldAddAvailableProduct() throws Exception {

        Product product = (Product) productRepository.load(pic.getNumber());

        PurchaseProcess purchaseProcess = new PurchaseProcess();
        purchaseProcess.add(client.getNumber(), product.getNumber());

        try {
            purchaseProcess.add(client.getNumber(), product.getNumber());
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Test
    public void calculateOffer() throws Exception {

    }

    @Test
    public void confirm() throws Exception {

    }

    @Test
    public void confirm1() throws Exception {

    }

}