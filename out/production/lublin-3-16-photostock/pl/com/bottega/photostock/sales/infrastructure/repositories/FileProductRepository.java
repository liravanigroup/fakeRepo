package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.commons.math.model.Fraction;
import pl.com.bottega.photostock.sales.model.deal.Money;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;
import pl.com.bottega.photostock.sales.model.products.AbstractProduct.ProductType;
import pl.com.bottega.photostock.sales.model.products.Product;
import pl.com.bottega.photostock.sales.model.products.ProductFactory;
import pl.com.bottega.photostock.sales.model.users.Client;
import pl.com.bottega.photostock.sales.model.usertool.Reservation;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static pl.com.bottega.photostock.sales.model.products.AbstractProduct.ProductType.valueOf;


public class FileProductRepository implements Repository<Product> {

    private final String path;
    private final String title = "name,number,price_fraction,currency,available,tags,length,resolution,type";

    public FileProductRepository(String path) {
        this.path = Paths.get(path).toAbsolutePath().toString();
    }

    @Override
    public Product load(String number) {
        String line="";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0)
                    return null;
                if (line.contains(number)) {
                    Product product = parseProduct(line);
                    if (product.getNumber().equals(number))
                        return product;
                }
            }
        } catch (Exception ex) {
            throw new DataAccessException(ex, "Invalid line of data: " + line);
        }
        return null;
    }


    private Product parseProduct(String line) {
        String[] components = line.split(",");
        String name = components[0];
        String number = components[1];
        Money price = new Money(new Fraction(components[2]), Money.Currency.valueOf(components[3]));
        boolean isAvailable = Boolean.parseBoolean(components[4]);
        String[] tags = components[5].split(" ");
        String lenght = components[6];
        String resolution = components[7];
        ProductType productType = valueOf(components[8].toUpperCase());

        return ProductFactory.getProductInstance(productType, name, number, price, isAvailable, tags);
    }

    @Override
    public void save(Product product) {
        writeProduct(product);
    }

    private File copyFile() throws IOException {
        Path pathSource = Paths.get(path);
        Path pathDestination = Paths.get(path.replaceAll("products.csv","tmp.csv"));
        Files.copy(pathSource, pathDestination);
        return new File(pathDestination.toString());
    }

    private void writeProduct(Product product) {
        File file = new File(path);
        String line;
        int lineNumber = 0;
        boolean isFileExists = !file.exists();

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(file, true), true); BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            if (isFileExists) { writer.println(title);}
            while ((line = reader.readLine()) != null) {
                if (line.contains(product.getNumber())) {
                    if (product.equals(parseProduct(line)))
                        throw new IllegalArgumentException("Product already exists");
                    else {
                        updateProduct(product, lineNumber);
                        return;
                    }
                }
                lineNumber++;
            }
            writer.println(getCsvLine(product));
        } catch (Exception ex) {
            throw new DataAccessException(ex, "Invalid line of data");
        }
    }

    private String getCsvLine(Product product) {
        return String.join(",", (CharSequence[]) product.export());
    }


    private void updateProduct(Product product, int counter) throws IOException {
        String line;
        File copy = copyFile();
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(path, false), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(copy)))) {
            int innerCounter = 0;
            while ((line = reader.readLine()) != null) {
                if (innerCounter == counter) {
                    writer.println(getCsvLine(product));
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

    public List<Product> find(String productName, String productNumber, Money minPrice, Money maxPrice, String... tags) {
        List<Product> result = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String line;
            while((line = reader.readLine()) != null){
                if(line.endsWith("type"))
                    continue;
                result.add(parseProduct(line));
            }
        }catch (Exception ex){
            /*NOP*/
        }
        System.out.println(result);
        return new ArrayList<>();
    }

    @Override
    public List<Product> find(String number) {
        return null;
    }

    @Override
    public Reservation findOpenedPer(Client client) {
        throw new UnsupportedOperationException();
    }
}
