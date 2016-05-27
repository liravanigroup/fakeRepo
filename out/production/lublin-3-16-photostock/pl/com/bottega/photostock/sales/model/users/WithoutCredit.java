package pl.com.bottega.photostock.sales.model.users;

import pl.com.bottega.photostock.sales.model.deal.Money;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-13.
 */

public class WithoutCredit implements PaymentStrategy {
    @Override
    public boolean canAfford(Money offerAmount, Client client) {
        return client.getSaldo().isGreaterOrEqualsThan(offerAmount);
    }

    @Override
    public void charge(Money offerTotalCost, String title, Client client) {
        if (canAfford(offerTotalCost, client))
            client.getSaldo().substract(offerTotalCost);
    }

    @Override
    public void recharge(Money money, Client client) {
        client.getSaldo().add(money);
    }
}
