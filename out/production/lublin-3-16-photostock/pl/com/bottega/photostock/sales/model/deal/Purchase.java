package pl.com.bottega.photostock.sales.model.deal;

import pl.com.bottega.photostock.sales.model.products.Product;
import pl.com.bottega.photostock.sales.model.users.Client;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Purchase {
    private final String number = UUID.randomUUID().toString();
    private final Client owner;
    private Date purchaseData = new Date();
    private final List<Product> products;
    private Money totalCost;

    public Purchase(Client owner, List<Product> products) {
        this.owner = owner;
        this.products = products;
        this.totalCost = calculateTotalCost();
    }

    public void setTotalCost(Money totalCost) {
        this.totalCost = totalCost;
    }

    private Money calculateTotalCost() {
        if (getProductsCount() == 0)
            throw new IllegalStateException("Order have not products");
        Money result = new Money(0, getPurchaseCurrency());
        for (Product product : products)
            result = result.add(product.calculatePrice());
        return result;
    }

    private Money.Currency getPurchaseCurrency(){
        return products.get(0).calculatePrice().getCurrency();
    }

    public String getNumber() {
        return number;
    }

    public Client getOwner() {
        return owner;
    }

    public Date getPurchaseData() {
        return purchaseData;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Money getTotalCost() {
        return totalCost;
    }

    public int getProductsCount(){
        return products.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Purchase purchase = (Purchase) o;

        return getTotalCost().equals(purchase.getTotalCost()) && number.equals(purchase.number) && owner.equals(purchase.owner) && purchaseData.equals(purchase.purchaseData) && products.equals(purchase.products);
    }

    @Override
    public int hashCode() {
        int result = number.hashCode();
        result = 31 * result + owner.hashCode();
        result = 31 * result + purchaseData.hashCode();
        result = 31 * result + products.hashCode();
        return result;
    }


    public String[] export(){
        String number = getNumber();
        String owner = getOwner().getNumber();
        String date = String.valueOf(purchaseData.getTime());
        String products = getProductsString();
        String totalCost = calculateTotalCost().getPriceFraction();
        String currency =  getTotalCost().getCurrency().toString();

        return new String[]{number, owner, date, products, totalCost, currency};
    }

    private String getProductsString() {
        if (getProducts().size() == 0)
            return "";
        StringBuilder builder = new StringBuilder();
        for (Product product : products) {
            builder.append(product.getNumber());
            builder.append("|");
        }
        return builder.toString();
    }

    public void setTime(Date date) {
        purchaseData = date;
    }
}
