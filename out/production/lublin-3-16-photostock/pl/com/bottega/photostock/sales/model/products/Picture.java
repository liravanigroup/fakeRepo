package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.deal.Money;

import java.util.Arrays;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-07.
 */

public class Picture extends AbstractProduct {

    private final String resolution = "3000x4000";

    public Picture(String name, String productCode, Money price, boolean isAvailable, String... tags) {
        super(name, productCode, price, isAvailable, tags);
    }

    private String getResolution() {
        return resolution;
    }

    private String getProductType() {
        return getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return "Picture{" +
                "name='" + super.getName() + '\'' +
                "number='" + super.getNumber() + '\'' +
                "price_cents='" + super.calculatePrice().getPriceFraction() + '\'' +
                "available='" + super.isAvailable() + '\'' +
                "tags='" + Arrays.toString(super.getTags()) + '\'' +
                "lenght='-'" +
                "resolution='" + resolution + '\'' +
                "type='" + getClass().getSimpleName() + '\'' +
                "}";
    }

    @Override
    public String[] export() {
        String[] result = super.export();
        result[7] = getResolution();
        result[8] = getProductType();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Picture)) return false;
        if (!super.equals(o)) return false;

        Picture picture = (Picture) o;

        return resolution.equals(picture.resolution);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + resolution.hashCode();
        return result;
    }
}
