package pl.com.bottega.photostock.sales.api;

import org.junit.Assert;
import org.junit.Test;
import pl.com.bottega.photostock.sales.infrastructure.repositories.ClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.LightBoxRepository;
import pl.com.bottega.photostock.sales.model.deal.Money;
import pl.com.bottega.photostock.sales.model.users.Client;
import pl.com.bottega.photostock.sales.model.usertool.LightBox;

/**
 * Created by Robert Pitucha on 14.05.2016.
 */
public class LightBoxManagementTest {

    Client client = new Client("Pan", "Panski", "Nothing", Money.ZERO_PL);
    ClientRepository clientRepository = new ClientRepository();
    LightBoxRepository lightBoxRepository = new LightBoxRepository();

    {

        clientRepository.save(client);

    }

    @Test
    public void createLightBox() throws Exception {
        LightBoxManagement lightBoxManagement = new LightBoxManagement();
        String numberLBX = lightBoxManagement.createLightBox(client.getNumber(),"MyLBX");
        LightBox loadLBX = lightBoxRepository.load(numberLBX);
        boolean result = loadLBX == null ? false : true;
        Assert.assertTrue(result);
    }

    @Test
    public void addToLightBox() throws Exception {

    }

    @Test
    public void share() throws Exception {

    }

    @Test
    public void reserve() throws Exception {

    }



}