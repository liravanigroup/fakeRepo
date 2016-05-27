package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.deal.Money;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;
import pl.com.bottega.photostock.sales.model.products.Product;
import pl.com.bottega.photostock.sales.model.users.Client;
import pl.com.bottega.photostock.sales.model.usertool.Reservation;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static pl.com.bottega.photostock.sales.infrastructure.repositories.RepositoryType.FILE_REPOSITORY;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-27.
 */

public class FileReservationRepository implements Repository<Reservation> {

    private final String path;
    private final String title = "number,owner,date,products,closed";

    public FileReservationRepository(String path) {
        this.path = Paths.get(path).toAbsolutePath().toString();
    }

    @Override
    public Reservation load(String number) {
        String line = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0)
                    return null;
                if (line.contains(number)) {
                    Reservation purchase = parseReservation(line);
                    if (purchase.getNumber().equals(number))
                        return purchase;
                }
            }
        } catch (Exception ex) {
            throw new DataAccessException(ex, "Invalid line of data: " + line);
        }
        return null;
    }

    private Reservation parseReservation(String line) {
        String[] components = line.split(",");

        String number = components[0];
        String ownerNumber = components[1];
        Date date = new Date();
        date.setTime(Long.parseLong(components[2]));
        String[] productsNumbers = components[3].split("|");
        boolean closed = Boolean.parseBoolean(components[4]);

        return buildReservation(number, ownerNumber, date, productsNumbers, closed);
    }

    private Reservation buildReservation(String number, String ownerNumber, Date date, String[] productsNumbers, boolean closed) {
        RepositoryFactory repositoryFactory = AbstractRepositoryFactory.getRepositoryByType(FILE_REPOSITORY);
        Repository clientRepository = repositoryFactory.getClientRepository();
        Repository productRepository = repositoryFactory.getProductRepository();
        Repository reservationRepository = repositoryFactory.getReservationRepository();

        Client client = (Client) clientRepository.load(ownerNumber);
        Reservation result = new Reservation(client);

        if (productsNumbers.length > 0) {
            for (String productsNumber : productsNumbers) {
                result.add((Product) productRepository.load(productsNumber));
            }
        }

        result.setDate(date);
        return result;
    }


    @Override
    public void save(Reservation Reservation) {
        writeProduct(Reservation);
    }

    private File copyFile() throws IOException {
        Path pathSource = Paths.get(path);
        Path pathDestination = Paths.get(path.replaceAll("reservations.csv", "tmp_reservations.csv"));
        Files.copy(pathSource, pathDestination);
        return new File(pathDestination.toString());
    }

    private void writeProduct(Reservation purchase) {
        File file = new File(path);
        String line;
        int lineNumber = 0;
        boolean isFileExists = !file.exists();

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(file, true), true); BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            if (isFileExists) {
                writer.println(title);
            }
            while ((line = reader.readLine()) != null) {
                if (line.contains(purchase.getNumber())) {
                    if (purchase.equals(parseReservation(line)))
                        throw new IllegalArgumentException("Product already exists");
                    else {
                        updateReservation(purchase, lineNumber);
                        return;
                    }
                }
                lineNumber++;
            }
            writer.println(getCsvLine(purchase));
        } catch (Exception ex) {
            throw new DataAccessException(ex, "Invalid line of data");
        }
    }

    private String getCsvLine(Reservation purchase) {
        return String.join(",", (CharSequence[]) purchase.export());
    }

    private void updateReservation(Reservation purchase, int counter) throws IOException {
        String line;
        File copy = copyFile();
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(path, false), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(copy)))) {
            int innerCounter = 0;
            while ((line = reader.readLine()) != null) {
                if (innerCounter == counter) {
                    writer.println(getCsvLine(purchase));
                } else {
                    writer.println(line);
                }
                innerCounter++;
            }
        } catch (Exception ex) {
            /*NOP*/
        }
        copy.delete();
    }

    public List<Reservation> find(String productName, String productNumber, Money minPrice, Money maxPrice, String... tags) {
        List<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.endsWith("type"))
                    continue;
                result.add(parseReservation(line));
            }
        } catch (Exception ex) {
            /*NOP*/
        }
        System.out.println(result);
        return new ArrayList<>();
    }

    @Override
    public List<Reservation> find(String number) {
        return null;
    }

    @Override
    public Reservation findOpenedPer(Client client) {
        throw new UnsupportedOperationException();
    }
}
