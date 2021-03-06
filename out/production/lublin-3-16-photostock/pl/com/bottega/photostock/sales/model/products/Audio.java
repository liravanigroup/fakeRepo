package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.deal.Money;

import java.util.Arrays;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-12.
 */

public class Audio extends AbstractProduct {

    private final long lenght = 1000 * 60 * 60 * 2;

    public Audio(String name, String productCode, Money price, boolean isAvailable, String... tags) {
        super(name, productCode, price, isAvailable, tags);
    }

    private String getLenght() {
        return String.valueOf(lenght);
    }

    private String getProductType() {
        return getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return "Audio{" +
                "name='" + super.getName() + '\'' +
                "number='" + super.getNumber() + '\'' +
                "price_cents='" + super.calculatePrice().getPriceFraction() + '\'' +
                "available='" + super.isAvailable() + '\'' +
                "tags='" + Arrays.toString(super.getTags()) + '\'' +
                "lenght='" + lenght + '\'' +
                "resolution='-'" +
                "type='" + getClass().getSimpleName() + '\'' +
                '}';
    }

    @Override
    public String[] export() {
        String[] result = super.export();
        result[6] = getLenght();
        result[8] = getProductType();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Audio)) return false;
        if (!super.equals(o)) return false;

        Audio audio = (Audio) o;

        return lenght == audio.lenght;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (lenght ^ (lenght >>> 32));
        return result;
    }
}
