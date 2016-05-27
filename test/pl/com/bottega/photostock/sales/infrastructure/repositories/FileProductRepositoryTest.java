package pl.com.bottega.photostock.sales.infrastructure.repositories;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.com.bottega.photostock.sales.model.deal.Money;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;
import pl.com.bottega.photostock.sales.model.products.Product;
import pl.com.bottega.photostock.sales.model.products.ProductFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static pl.com.bottega.photostock.sales.infrastructure.repositories.RepositoryType.FILE_REPOSITORY;
import static pl.com.bottega.photostock.sales.model.deal.Money.Currency.ADP;
import static pl.com.bottega.photostock.sales.model.deal.Money.Currency.USD;
import static pl.com.bottega.photostock.sales.model.products.AbstractProduct.ProductType.*;


public class FileProductRepositoryTest {

    private final static Product INITIAL_PRODUCT = ProductFactory.getProductInstance(PICTURE, "name", "245", new Money(10), true, "Tag2", "Tag3");
    private final static Product INITIAL_PRODUCT_CLONE = ProductFactory.getProductInstance(PICTURE, "name", "245", new Money(10), true, "Tag2", "Tag3");
    private final static Product INITIAL_PRODUCT_UPDATED = ProductFactory.getProductInstance(PICTURE, "name", "245", new Money(15), true, "Tag2", "Tag3");
    private final static Product INITIAL_PICTURE = ProductFactory.getProductInstance(PICTURE, "Car", "nr1358", new Money(25), true, "Tag2", "Tag3");
    private final static Product INITIAL_VIDEO = ProductFactory.getProductInstance(VIDEO, "Dog", "nr1356", new Money(10.5, ADP), true, "Tag2", "Tag3");
    private final static Product INITIAL_AUDIO = ProductFactory.getProductInstance(AUDIO, "Cat", "nr1359", new Money(15, 95, USD), true, "Tag2", "Tag3");

    private static Path path = Paths.get("test/fixtures/products.csv");
    private static Path pathTmp = Paths.get("test/fixtures/tmp/tmp.csv");
    private static RepositoryFactory repositoryFactory = AbstractRepositoryFactory.getRepositoryByType(FILE_REPOSITORY);
    private static Repository fileProductRepository = repositoryFactory.getProductRepository();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void shouldDeleteFile() {
        File file = new File(path.toAbsolutePath().toString());
        File fileCopy = new File(pathTmp.toAbsolutePath().toString());
        if (file.exists())
            file.delete();
        if (fileCopy.exists())
            fileCopy.delete();
    }

    @Test
    public void shouldLoadProduct() {
        fileProductRepository.save(INITIAL_PRODUCT);
        Product loadedProduct = (Product) fileProductRepository.load(INITIAL_PRODUCT.getNumber());
        Assert.assertEquals(INITIAL_PRODUCT, loadedProduct);
    }

    @Test
    public void shouldThrowDataAccessExceptionWhenFileNotFound() {
        thrown.expect(DataAccessException.class);
        Repository repository = new FileProductRepository("fake path");
        repository.load("135");
    }

    @Test
    public void shouldThrowDataAccessException() {
        thrown.expect(DataAccessException.class);
        thrown.expectMessage("Product already exists");
        fileProductRepository.save(INITIAL_PRODUCT);
        fileProductRepository.save(INITIAL_PRODUCT_CLONE);
    }

    @Test
    public void shouldWriteProducts() {
        fileProductRepository.save(INITIAL_PICTURE);
        fileProductRepository.save(INITIAL_VIDEO);
        fileProductRepository.save(INITIAL_AUDIO);

        Product loadedProduct1 = (Product) fileProductRepository.load(INITIAL_PICTURE.getNumber());
        Product loadedProduct2 = (Product) fileProductRepository.load(INITIAL_VIDEO.getNumber());
        Product loadedProduct3 = (Product) fileProductRepository.load(INITIAL_AUDIO.getNumber());

        Assert.assertEquals(INITIAL_PICTURE, loadedProduct1);
        Assert.assertEquals(INITIAL_VIDEO, loadedProduct2);
        Assert.assertEquals(INITIAL_AUDIO, loadedProduct3);
    }

    @Test
    public void shouldUpdateProductPriceByNumber() {
        fileProductRepository.save(INITIAL_AUDIO);
        fileProductRepository.save(INITIAL_PICTURE);
        fileProductRepository.save(INITIAL_PRODUCT);
        fileProductRepository.save(INITIAL_VIDEO);
        fileProductRepository.save(INITIAL_PRODUCT_UPDATED);

        Product result = (Product) fileProductRepository.load(INITIAL_PRODUCT.getNumber());

        Assert.assertEquals(INITIAL_PRODUCT_UPDATED, result);
    }

    @Test
    public void shouldFindProductsByParameters(){
        fileProductRepository.save(INITIAL_AUDIO);
        fileProductRepository.save(INITIAL_PICTURE);
        fileProductRepository.save(INITIAL_PRODUCT);
        fileProductRepository.save(INITIAL_VIDEO);
        fileProductRepository.save(INITIAL_PRODUCT_UPDATED);


    }
}
