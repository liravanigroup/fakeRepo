package pl.com.bottega.photostock.sales.model.deal;

import pl.com.bottega.photostock.sales.model.products.Product;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Offer {
    private List<Product> products = new LinkedList<>();
    private Date date = new Date();
    private boolean isAgreeByClient = false;

    public Offer(List<Product> products) {
        validateNotNull();
        this.products = products;
    }

    public boolean sameAs(Offer offer, double percentDifferent) {
        if (this == offer) return true;
        if (offer == null || getClass() != offer.getClass()) return false;
        if (getTotalCost().equals(offer.getTotalCost()) && products.equals(offer.products)) return true;
        return products.equals(offer.products) && getTotalCost().getPercentDifferent(offer.getTotalCost()) <= percentDifferent;
    }

    public List<Product> getItems() {
        List<Product> result = products.stream().filter(Product::isAvailable).collect(Collectors.toList());
        return sortItems(result);
    }

    private List<Product> sortItems(List<Product> products) {
        Collections.sort(products, (o1, o2) -> o1.getNumber().compareTo(o2.getNumber()));
        return products;
    }

    public Money getTotalCost() {
        validateNotNull();
        Money result = new Money(0, products.get(0).calculatePrice().getCurrency());
        for (Product product : products)
            result = result.add(product.calculatePrice());
        return result;
    }

    public int getItemsCount() {
        validateNotNull();
        return products.size();
    }

    private void validateNotNull() {
        if (products == null)
            throw new IllegalArgumentException("Products count is zero");
    }

}
