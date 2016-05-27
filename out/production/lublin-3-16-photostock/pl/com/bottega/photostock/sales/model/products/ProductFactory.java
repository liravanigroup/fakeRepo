package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.deal.Money;
import pl.com.bottega.photostock.sales.model.products.AbstractProduct.ProductType;

import java.util.UUID;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-13.
 */

public class ProductFactory {
    public static Product getProductInstance(ProductType productType, String name, String number, Money price, boolean isAvailable, String... tags) {
        validate(productType, name, price, tags);
        number = getProductCode(number);
        switch (productType) {
            case VIDEO:
                return new Video(name, number, price, isAvailable, tags);
            case PICTURE:
                return new Picture(name, number, price, isAvailable, tags);
            case AUDIO:
                return new Audio(name, number, price, isAvailable, tags);
            default:
                throw new IllegalArgumentException("Please entry correct product type");
        }
    }

    private static void validate(ProductType productType, String name, Money price, String... tags) {
        if (productType == null)
            throw new IllegalArgumentException("Please select product type");
        if (name == null || name.length() == 0)
            throw new IllegalArgumentException("Please entry correct product name");
        validateTags(tags);
        if (price == null /*|| price.isGreaterOrEqualsThan(new Money(0, price.getCurrency()))*/)
            throw new IllegalArgumentException("Please entry correct product price");
    }

    private static void validateTags(String... tags) {
        if (tags == null)
            throw new IllegalArgumentException("Please entry product tags");
        else if (tags.length == 0 || tags[0].equals(""))
            throw new IllegalArgumentException("Please entry correct product tags");
    }

    private static String getProductCode(String productCode) {
        return productCode == null || productCode.length() == 0 ? UUID.randomUUID().toString() : productCode;
    }
}
