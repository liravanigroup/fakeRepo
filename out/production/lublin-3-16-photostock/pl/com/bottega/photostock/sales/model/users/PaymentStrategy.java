package pl.com.bottega.photostock.sales.model.users;

import pl.com.bottega.photostock.sales.model.deal.Money;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-13.
 */

public interface PaymentStrategy {
    boolean canAfford(Money offerAmount, Client client);
    void charge(Money offerAmount, String title, Client client);
    void recharge(Money addMoney, Client client);
}
