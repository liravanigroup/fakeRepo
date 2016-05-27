package pl.com.bottega.photostock.sales.api;

import org.junit.Assert;
import org.junit.Test;
import pl.com.bottega.photostock.sales.infrastructure.repositories.ProductRepository;
import pl.com.bottega.photostock.sales.model.deal.Money;
import pl.com.bottega.photostock.sales.model.products.Product;
import pl.com.bottega.photostock.sales.model.products.ProductFactory;

import java.util.List;

import static pl.com.bottega.photostock.sales.model.products.AbstractProduct.ProductType.PICTURE;

/**
 * Created by Robert Pitucha on 14.05.2016.
 */
public class ProductsCatalogTest {
    ProductRepository productRepository = new ProductRepository();
    Product picture = ProductFactory.getProductInstance(PICTURE, "Car", "", new Money(12), true, "Car", "Auto");

    {


        productRepository.save(picture);

    }

    @Test
    public void find() throws Exception {
        ProductsCatalog productsCatalog = new ProductsCatalog();
        List<Product> result = productsCatalog.find("Car", null, null, null);
        Assert.assertTrue(result.contains(picture));
    }

    @Test
    public void findNothing() {
        ProductsCatalog productsCatalog = new ProductsCatalog();
        List<Product> answer = productsCatalog.find("Cat", null, null, null);
        boolean result = false;
        if (answer.size() == 0)
            result = true;
        Assert.assertTrue(result);
    }

}