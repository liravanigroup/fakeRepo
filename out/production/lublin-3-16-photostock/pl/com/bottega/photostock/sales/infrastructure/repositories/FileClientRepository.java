package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.deal.Money;
import pl.com.bottega.photostock.sales.model.users.Client;
import pl.com.bottega.photostock.sales.model.usertool.Reservation;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-27.
 */

public class FileClientRepository implements Repository<Client>{

    private final Path path;
    private final String title = "name,address,number,credit_limit,debt,saldo,isActive,company,status";

    public FileClientRepository(String path) {
        this.path = Paths.get(path);
    }



    @Override
    public Client load(String clientNumber) {
        return null;
    }

    @Override
    public void save(Client client) {
        File file = new File(path.toAbsolutePath().toString());
        boolean isFileExists = !file.exists();
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(file), true)) {
            if(isFileExists)
                writer.println(title);
            writer.println(getCsvLine(client));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Client> find(String productName, String productNumber, Money minPrice, Money maxPrice, String... tags) {
        return null;
    }

    private String getCsvLine(Client client) {
        return String.join(",", client.export());
    }

    @Override
    public List find(String number) {
        return null;
    }

    @Override
    public Reservation findOpenedPer(Client client) {
        throw new UnsupportedOperationException();
    }
}
