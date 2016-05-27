package pl.com.bottega.photostock.sales.infrastructure.repositories;

import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.photostock.sales.model.deal.Money;
import pl.com.bottega.photostock.sales.model.products.Product;
import pl.com.bottega.photostock.sales.model.products.ProductFactory;

import static pl.com.bottega.photostock.sales.model.products.AbstractProduct.ProductType.*;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-11.
 */

public class ProductRepositoryTest {


    @Before
    public void shouldCreateNewProducts() throws Exception {
        Product picture = ProductFactory.getProductInstance(PICTURE, "Cats", "nr25", new Money(10), true, "Cats", "Kitty");
        Product video = ProductFactory.getProductInstance(VIDEO, "Cats", "nr26", new Money(10), true, "Cats", "Kitty");
        Product audio = ProductFactory.getProductInstance(AUDIO, "Cats", "nr27", new Money(10), true, "Cats", "Kitty");
    }


    @Test
    public void load() {


    }

    @Test
    public void save() {

    }

    @Test
    public void selectByPrice() {

    }

    @Test
    public void selectByName() {

    }

    @Test
    public void selectByTags() {

    }

}