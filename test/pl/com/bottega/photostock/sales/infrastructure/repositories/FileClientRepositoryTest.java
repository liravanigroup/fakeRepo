package pl.com.bottega.photostock.sales.infrastructure.repositories;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.photostock.sales.model.deal.Money;
import pl.com.bottega.photostock.sales.model.users.Client;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-27.
 */

public class FileClientRepositoryTest {

    private static final Client CLIENT = new Client("Vasia", "Pupkin", "Kiev", new Money(0));
    private static Path path = Paths.get("test/fixtures/clients.csv");
    private Repository clientRepository = new FileClientRepository(path.toString());

    @Before
    public void deleteFiles(){
        File file = new File(path.toAbsolutePath().toString());
        if(file.exists())
            file.delete();
    }

    @Test
    public void load() throws Exception {
        clientRepository.save(CLIENT);
        Client loadedClient = (Client) clientRepository.load(CLIENT.getNumber());
        Assert.assertEquals(CLIENT, loadedClient);
    }

    @Test
    public void save() throws Exception {
        clientRepository.save(CLIENT);
        //Client loadedClient = (Client) clientRepository.load(CLIENT.getNumber());
        //Assert.assertEquals(CLIENT, loadedClient);
    }

}