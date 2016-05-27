package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.deal.Money;
import pl.com.bottega.photostock.sales.model.users.Client;

import java.util.Arrays;


public abstract class AbstractProduct implements Product {
    private String name;
    private String number;
    private Money price;
    private String[] tags;
    private Client reservedСlient;
    private boolean isAvailable;

    AbstractProduct(String name, String number, Money price, boolean isAvailable, String... tags) {
        this.name = name;
        this.number = number;
        this.price = price;
        this.isAvailable = isAvailable;
        this.tags = tags;
    }

    @Override
    public void deactivate() {
        isAvailable = false;
    }

    @Override
    public void activate() {
        isAvailable = true;
    }

    private String getCurrencyCode() {
        return price.getCurrency().toString();
    }

    public String getName() {
        return name;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public void setNumber(String number) {
        this.number = number;
    }

    public String[] getTags() {
        return tags == null ? new String[0] : tags;
    }

    private String getAvailableStainString() {
        return String.valueOf(isAvailable());
    }

    private String getPriceFractionString() {
        return price.getPriceFraction();
    }

    private String getJoinedTags() {
        return String.join(" ", getTags());
    }

    @Override
    public Money calculatePrice() {
        return price;
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public void reservePer(Client client) {
        if (isAvailable && reservedСlient==null) {
            this.reservedСlient = client;
        } else {
            throw new IllegalStateException("Sorry this product is not available to reservation");
        }
    }

    @Override
    public void unReservePer(Client client) {
        if (isAvailable && reservedСlient.equals(client)) {
            this.reservedСlient = null;
        } else {
            throw new IllegalStateException("Sorry this product reserved per other client");
        }
    }

    @Override
    public String[] export() {
        String name = getName();
        String number = getNumber();
        String priceFraction = getPriceFractionString();
        String currencyCode = getCurrencyCode();
        String isAvailable = getAvailableStainString();
        String tags = getJoinedTags();
        String lenght = "";
        String resolution = "";
        String productType = "";
        return new String[]{name, number, priceFraction, currencyCode, isAvailable, tags, lenght, resolution, productType};
    }


    public enum ProductType {
        PICTURE, VIDEO, AUDIO
    }

//    private static class ComparatorByPrice implements Comparator<Product> {
//        @Override
//        public int compare(Product product1, Product product2) {
//            if (product1.calculatePrice().isGreaterOrEqualsThan(product2.calculatePrice()))
//                return 1;
//            return -1;
//        }
//    }
//
//    private static class ComparatorByName implements Comparator<Product> {
//        @Override
//        public int compare(Product product1, Product product2) {
//            return product1.getName().compareTo(product2.getName());
//        }
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractProduct)) return false;

        AbstractProduct that = (AbstractProduct) o;

        if (!name.equals(that.name)) return false;
        if (!number.equals(that.number)) return false;
        if (!price.equals(that.price)) return false;
        return Arrays.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + number.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + Arrays.hashCode(tags);
        return result;
    }
}

