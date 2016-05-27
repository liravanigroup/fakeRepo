package pl.com.bottega.photostock.sales.model.products;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import pl.com.bottega.photostock.sales.model.deal.Money;
import pl.com.bottega.photostock.sales.model.users.Client;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-25.
 */

public class ProductTest {

    private final static Client INITIAL_CLIENT_1 = new Client("Ivan", "Ivanov", "Lublin",Money.ZERO_PL);
    private final static Client INITIAL_CLIENT_2 = new Client("Armen", "Ivanov", "Lublin",Money.ZERO_PL);
    private static Product picture = new Picture("MyPicture", "1355", new Money(10), true, "Tag1", "Tag2", "Tag3");
    private static Product video = new Video("MyPicture", "1355", new Money(10), true, "Tag1", "Tag2", "Tag3");
    private static Product audio = new Audio("MyPicture", "1355", new Money(10), true, "Tag1", "Tag2", "Tag3");

    @Test
    public void calculatePrice() {
        Assert.assertEquals(picture.calculatePrice(), new Money(10));
    }

    @Test
    public void getTags() {
        Assert.assertArrayEquals(picture.getTags(), new String[]{"Tag1", "Tag2", "Tag3"});
    }

    @Test
    public void getName() {
        Assert.assertEquals(picture.getName(), "MyPicture");
    }

    @Test
    public void getNumber() {
        Assert.assertEquals(picture.getNumber(), "1355");
    }

    @Test
    public void shouldExportPicture() {
        String name = "MyPicture";
        String number = "1355";
        String priceInCents = "100/10";
        String currencyCode = "PLN";
        String isAvailable = "true";
        String tags = "Tag1 Tag2 Tag3";
        String lenght = "";
        String resolution = "3000x4000";
        String productType = "Picture";
        String[] testProduct = new String[]{name, number, priceInCents, currencyCode, isAvailable, tags, lenght, resolution, productType};

        Assert.assertArrayEquals(testProduct, picture.export());
    }

    @Test
    public void shouldExportVideo() {
        String name = "MyPicture";
        String number = "1355";
        String priceInCents = "100/10";
        String currencyCode = "PLN";
        String isAvailable = "true";
        String tags = "Tag1 Tag2 Tag3";
        String lenght = "7200000";
        String resolution = "1920x1080";
        String productType = "Video";
        String[] testProduct = new String[]{name, number, priceInCents, currencyCode, isAvailable, tags, lenght, resolution, productType};
        Assert.assertArrayEquals(video.export(), testProduct);
    }

    @Test
    public void shouldExportAudio() {
        String name = "MyPicture";
        String number = "1355";
        String priceInCents = "100/10";
        String currencyCode = "PLN";
        String isAvailable = "true";
        String tags = "Tag1 Tag2 Tag3";
        String lenght = "7200000";
        String resolution = "";
        String productType = "Audio";
        String[] testProduct = new String[]{name, number, priceInCents, currencyCode, isAvailable, tags, lenght, resolution, productType};
        Assert.assertArrayEquals(audio.export(), testProduct);
    }

    @Test
    public void isAvailable() {
        Assert.assertTrue(picture.isAvailable());
    }

    @Test
    public void setNumber() {
        picture.setNumber("1355");
        Assert.assertEquals("1355", picture.getNumber());
    }

    @Ignore
    @Test
    public void shouldReservePerClient() {
        picture.reservePer(INITIAL_CLIENT_1);
    }
    @Ignore
    @Test
    public void unReservePer() {
        picture.reservePer(INITIAL_CLIENT_1);
    }
    @Ignore
    @Test
    public void shouldDeactivateProduct() {
        picture.deactivate();
        Assert.assertFalse(picture.isAvailable());
    }
    @Ignore
    @Test
    public void shouldActivateProduct() {
        picture.activate();
        Assert.assertTrue(picture.isAvailable());
    }
}