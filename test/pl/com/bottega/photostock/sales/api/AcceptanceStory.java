package pl.com.bottega.photostock.sales.api;

import org.junit.Test;
import pl.com.bottega.photostock.sales.infrastructure.repositories.*;
import pl.com.bottega.photostock.sales.model.deal.Money;
import pl.com.bottega.photostock.sales.model.deal.Offer;
import pl.com.bottega.photostock.sales.model.products.Picture;
import pl.com.bottega.photostock.sales.model.products.Product;
import pl.com.bottega.photostock.sales.model.users.Client;
import pl.com.bottega.photostock.sales.model.usertool.LightBox;
import pl.com.bottega.photostock.sales.model.usertool.Reservation;

import java.util.List;

import static pl.com.bottega.photostock.sales.infrastructure.repositories.RepositoryType.FAKE_REPOSITORY;
import static pl.com.bottega.photostock.sales.model.products.AbstractProduct.ProductType.PICTURE;

/**
 * Created by Robert Pitucha on 14.05.2016.
 */
public class AcceptanceStory {


    public static final Money INITIAL_MONEY = new Money(1_000);
    private RepositoryFactory repositoryFactory = AbstractRepositoryFactory.getRepositoryByType(FAKE_REPOSITORY);
    Repository productRepository = repositoryFactory.getProductRepository();
    Repository clientRepository = repositoryFactory.getClientRepository();
    Repository lightBoxRepo = repositoryFactory.getLightBoxRepository();
    @Test
    public void story() {

        ProductsCatalog productsCatalog = new ProductsCatalog();
        ClientManagement clientManagement = new ClientManagement();

        String clientNumber = clientManagement.register("Jan", "Janusz", "Pan","777@wp.pl" ,"Miasto");

        Client client = (Client) clientRepository.load(clientNumber);

        clientManagement.recharge(client.getNumber(), INITIAL_MONEY);

        AdminPanel adminPanel = new AdminPanel();
        adminPanel.addProduct(PICTURE, "Car", "", new Money(10), "car", "auto", "audi");
        adminPanel.addProduct(PICTURE, "Auto", "", new Money(320), "car", "auto", "audi");
        adminPanel.addProduct(PICTURE, "Bus", "", new Money(100), "car", "auto", "audi");
        adminPanel.addProduct(PICTURE, "Busss", "", new Money(170), "car", "auto", "audi");
        adminPanel.addProduct(PICTURE, "Buss", "", new Money(160), "car", "auto", "audi");
        adminPanel.addProduct(PICTURE, "Dog", "", new Money(190), "dog", "auto", "audi");
        adminPanel.addProduct(PICTURE, "CAt", "", new Money(180), "cat", "auto", "audi");

        List<Product> products = productsCatalog.find("Busss", Money.ZERO_PL, INITIAL_MONEY, "car");

        LightBoxManagement lightBoxManagement = new LightBoxManagement();
        String lbxNum = lightBoxManagement.createLightBox(client.getNumber(), "MyLBX");
        LightBox lbx = (LightBox) lightBoxRepo.load(lbxNum);

        PurchaseProcess purchaseProcess = new PurchaseProcess();

        for (Product product : products) {
            if (product instanceof Picture) {
                lbx.add((Picture) product);
            }
            purchaseProcess.add(client.getNumber(), product.getNumber());
        }


        Reservation reservation = ReservationRepository.findOpenPer(client);


        Offer offer = purchaseProcess.calculateOffer(reservation.getNumber());

        System.out.println(offer.getTotalCost());

        purchaseProcess.confirm(reservation.getNumber());
    }

}
