package pl.com.bottega.photostock.sales.model.products;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.com.bottega.photostock.sales.model.deal.Money;

import static org.junit.Assert.fail;
import static pl.com.bottega.photostock.sales.model.products.AbstractProduct.ProductType.*;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-25.
 */

public class ProductFactoryTest {

    private final static Money INITIAL_MONEY = new Money(15);

    private final static String INITIAL_NAME = "Product name";
    private final static String INITIAL_EMPTY_NAME = "";

    private final static String[] INITIAL_TAGS = {"Product", "Picture", "Video", "Audio"};
    private final static String[] INITIAL_EMPTY_TAGS = {};
    private final static String[] INCORRECT_TAGS = {"", "", "", ""};

    private final static String INITIAL_CODE = "SKU123548";
    private final static String INITIAL_EMPTY_CODE = "";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldGetPicture() {
        Product product = ProductFactory.getProductInstance(PICTURE, INITIAL_NAME, INITIAL_CODE, INITIAL_MONEY, true, INITIAL_TAGS);
        if (!(product instanceof Picture))
            fail("Result product is not picture");
    }

    @Test
    public void shouldGetVideo() {
        Product product = ProductFactory.getProductInstance(VIDEO, INITIAL_NAME, INITIAL_CODE, INITIAL_MONEY, true, INITIAL_TAGS);
        if (!(product instanceof Video))
            fail("Result product is not video");
    }

    @Test
    public void shouldGetAudio() {
        Product product = ProductFactory.getProductInstance(AUDIO, INITIAL_NAME, INITIAL_CODE, INITIAL_MONEY, true, INITIAL_TAGS);
        if (!(product instanceof Audio))
            fail("Result product is not audio");
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionByProductType() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please select product type");
        Product product = ProductFactory.getProductInstance(null, INITIAL_NAME, INITIAL_CODE, INITIAL_MONEY, true, INITIAL_TAGS);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionByProductNullName() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please entry correct product name");
        Product product = ProductFactory.getProductInstance(VIDEO, null, INITIAL_CODE, INITIAL_MONEY, true, INITIAL_TAGS);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionByProductEmptyName() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please entry correct product name");
        Product product = ProductFactory.getProductInstance(AUDIO, INITIAL_EMPTY_NAME, INITIAL_CODE, INITIAL_MONEY, true, INITIAL_TAGS);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionByProductPrice() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please entry correct product price");
        Product product = ProductFactory.getProductInstance(VIDEO, INITIAL_NAME, INITIAL_CODE, Money.ZERO_PL, true, INITIAL_TAGS);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionByProductNullPrice() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please entry correct product price");
        Product product = ProductFactory.getProductInstance(PICTURE, INITIAL_NAME, INITIAL_CODE, null, true, INITIAL_TAGS);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionByProductNullTags() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please entry product tags");
        Product product = ProductFactory.getProductInstance(VIDEO, INITIAL_NAME, INITIAL_CODE, INITIAL_MONEY, true, null);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionByProductEmptyTags() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please entry correct product tags");
        Product product = ProductFactory.getProductInstance(VIDEO, INITIAL_NAME, INITIAL_CODE, INITIAL_MONEY, true, INITIAL_EMPTY_TAGS);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionByProductIncorrectTags() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please entry correct product tags");
        Product product = ProductFactory.getProductInstance(VIDEO, INITIAL_NAME, INITIAL_CODE, INITIAL_MONEY, true, INCORRECT_TAGS);
    }

    @Test
    public void shouldCreateCodeForProductWithNullCode() {
        Product product = ProductFactory.getProductInstance(VIDEO, INITIAL_NAME, null, INITIAL_MONEY, true, INITIAL_TAGS);
        Assert.assertNotNull(product.getNumber());
    }

    @Test
    public void shouldCreateCodeForProductWithEmptyCode() {
        Product product = ProductFactory.getProductInstance(VIDEO, INITIAL_NAME, INITIAL_EMPTY_CODE, INITIAL_MONEY, true, INITIAL_TAGS);
        Assert.assertNotEquals(product.getNumber(), "");
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionByProductNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please select product type");
        Product product = ProductFactory.getProductInstance(null, null, null, null, true, null);
    }
}