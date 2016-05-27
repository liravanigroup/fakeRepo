package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.deal.Money;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;
import pl.com.bottega.photostock.sales.model.products.Picture;
import pl.com.bottega.photostock.sales.model.users.Client;
import pl.com.bottega.photostock.sales.model.usertool.LightBox;
import pl.com.bottega.photostock.sales.model.usertool.Reservation;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static pl.com.bottega.photostock.sales.infrastructure.repositories.RepositoryType.FILE_REPOSITORY;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-27.
 */

public class FileLightBoxRepository implements Repository<LightBox> {

    private final String path;
    private final String title = "name,number,owner,pictures,isActive,admins";

    public FileLightBoxRepository(String path) {
        this.path = Paths.get(path).toAbsolutePath().toString();
    }


    @Override
    public LightBox load(String number) {
        String line = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0)
                    return null;
                if (line.contains(number)) {
                    LightBox lightBox = parseLightBox(line);
                    if (lightBox.getNumber().equals(number))
                        return lightBox;
                }
            }
        } catch (Exception ex) {
            throw new DataAccessException(ex, "Invalid line of data: " + line);
        }
        return null;
    }

    private LightBox parseLightBox(String line) {
        String[] components = line.split(",");

        String name = components[0];
        String number = components[1];
        String ownerNumber = components[2];
        String[] picturesNumbers = components[3].split("|");
        boolean isActive = Boolean.parseBoolean(components[4]);
        String[] adminsNumbers = components[5].split("|");

        return buildLightBox(name, number, ownerNumber, picturesNumbers, isActive, adminsNumbers);
    }

    private LightBox buildLightBox(String name, String number, String ownerNumber, String[] picturesNumbers, boolean isActive, String[] adminsNumbers) {
        RepositoryFactory repositoryFactory = AbstractRepositoryFactory.getRepositoryByType(FILE_REPOSITORY);
        Repository clientRepository = repositoryFactory.getClientRepository();
        Repository productRepository = repositoryFactory.getProductRepository();

        Client client = (Client) clientRepository.load(ownerNumber);
        LightBox result = new LightBox(client, name);

        if (picturesNumbers.length > 0) {
            for (String numberPicture : picturesNumbers) {
                result.add((Picture) productRepository.load(numberPicture));
            }
        }

        if (adminsNumbers.length > 0) {
            for (String adminNumber : adminsNumbers) {
                result.setCoOwner((Client) clientRepository.load(adminNumber));
            }
        }
        result.setNumber(number);
        result.setActive(isActive);
        return result;
    }


    @Override
    public void save(LightBox lightBox) {
        writeProduct(lightBox);
    }

    private File copyFile() throws IOException {
        Path pathSource = Paths.get(path);
        Path pathDestination = Paths.get(path.replaceAll("products.csv", "tmp.csv"));
        Files.copy(pathSource, pathDestination);
        return new File(pathDestination.toString());
    }

    private void writeProduct(LightBox lightBox) {
        File file = new File(path);
        String line;
        int lineNumber = 0;
        boolean isFileExists = !file.exists();

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(file, true), true); BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            if (isFileExists) {
                writer.println(title);
            }
            while ((line = reader.readLine()) != null) {
                if (line.contains(lightBox.getNumber())) {
                    if (lightBox.equals(parseLightBox(line)))
                        throw new IllegalArgumentException("Product already exists");
                    else {
                        updateLightBox(lightBox, lineNumber);
                        return;
                    }
                }
                lineNumber++;
            }
            writer.println(getCsvLine(lightBox));
        } catch (Exception ex) {
            throw new DataAccessException(ex, "Invalid line of data");
        }
    }

    private String getCsvLine(LightBox lightBox) {
        return String.join(",", (CharSequence[]) lightBox.export());
    }

    private void updateLightBox(LightBox lightBox, int counter) throws IOException {
        String line;
        File copy = copyFile();
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(path, false), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(copy)))) {
            int innerCounter = 0;
            while ((line = reader.readLine()) != null) {
                if (innerCounter == counter) {
                    writer.println(getCsvLine(lightBox));
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

    public List<LightBox> find(String productName, String productNumber, Money minPrice, Money maxPrice, String... tags) {
        List<LightBox> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.endsWith("type"))
                    continue;
                result.add(parseLightBox(line));
            }
        } catch (Exception ex) {
            /*NOP*/
        }
        System.out.println(result);
        return new ArrayList<>();
    }

    @Override
    public List<LightBox> find(String number) {
        return null;
    }

    @Override
    public Reservation findOpenedPer(Client client) {
        throw new UnsupportedOperationException();
    }
}
