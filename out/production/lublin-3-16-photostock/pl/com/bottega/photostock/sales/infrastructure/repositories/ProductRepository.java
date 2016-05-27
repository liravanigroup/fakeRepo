package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.deal.Money;
import pl.com.bottega.photostock.sales.model.exceptions.ProductNotAvailableException;
import pl.com.bottega.photostock.sales.model.products.Product;
import pl.com.bottega.photostock.sales.model.users.Client;
import pl.com.bottega.photostock.sales.model.usertool.Reservation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductRepository implements Repository<Product> {

    public static Map<String, Product> productsDataBase = new HashMap<>();

    @Override
    public Product load(String nr) {
        Product result = productsDataBase.get(nr);
        if (result == null)
            throw new ProductNotAvailableException("Product nr " + nr + "does not exist", nr, ProductRepository.class);
        return result;
    }

    @Override
    public void save(Product product) {
        productsDataBase.put(product.getNumber(), product);
    }

    @Override
    public List<Product> find(String number) {
        return null;
    }

    @Override
    public Reservation findOpenedPer(Client client) {
        throw new UnsupportedOperationException();
    }


    public List<Product> selectBy(String name, Money from, Money to, String... tags) {
        Map<String, Product> result = new HashMap<>();
        result = selectByPrice(from, to);
        result = selectByName(result, name);
        result = selectByTags(result, tags);
        if (result.size() == 0)
            throw new IllegalArgumentException("No results by query");
        return new ArrayList<>(result.values());
    }

    public Map<String, Product> selectByPrice(Map<String, Product> products, Money from, Money to) {
        Map<String, Product> result = new HashMap<>();
        if (from == null && to == null)
            return products;
        else
            selectPrice(products, from, to, result);
        return result;
    }

    private void selectPrice(Map<String, Product> products, Money from, Money to, Map<String, Product> result) {
        if (from == null) {
            from = Money.ZERO_PL;
            selectPrice(products, from, to, result);
        } else if (to == null) {
            for (Map.Entry<String, Product> entry : products.entrySet()) {
                Money productPrice = entry.getValue().calculatePrice();
                if (productPrice.isGreaterOrEqualsThan(from))
                    result.put(entry.getKey(), entry.getValue());
            }
        } else {
            if (from.isGreaterThan(to))
                throw new IllegalArgumentException("Wrong price diapason");
            for (Map.Entry<String, Product> entry : products.entrySet()) {
                Money productPrice = entry.getValue().calculatePrice();
                if (productPrice.isGreaterOrEqualsThan(from) && productPrice.isLessOrEqualsThan(to))
                    result.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public Map<String, Product> selectByPrice(Money from, Money to) {
        return selectByPrice(ProductRepository.productsDataBase, from, to);
    }

    public Map<String, Product> selectByName(Map<String, Product> products, String name) {
        if (name == null)
            return products;
        Map<String, Product> result = new HashMap<>();
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            if (entry.getValue().getName().contains(name))
                result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public Map<String, Product> selectByName(String name) {
        return selectByName(ProductRepository.productsDataBase, name);
    }

    public Map<String, Product> selectByTags(Map<String, Product> products, String[] tags) {
        if (tags == null || tags.length == 0)
            return products;
        Map<String, Product> result = new HashMap<>();
        mark:
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            for (String productTag : entry.getValue().getTags()) {
                for (String tag : tags) {
                    if (productTag.contains(tag)) {
                        result.put(entry.getKey(), entry.getValue());
                        continue mark;
                    }
                }
            }
        }
        return result;
    }

    public Map<String, Product> selectByTags(String[] tags) {
        return selectByTags(ProductRepository.productsDataBase, tags);
    }
}
