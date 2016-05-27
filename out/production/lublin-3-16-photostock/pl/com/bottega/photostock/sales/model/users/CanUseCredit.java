package pl.com.bottega.photostock.sales.model.users;

import pl.com.bottega.photostock.sales.model.deal.Money;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-13.
 */

public class CanUseCredit implements PaymentStrategy {
    @Override
    public boolean canAfford(Money offerTotalCost, Client client) {
        return client.getSaldo().add(client.getCreditLimit()).isGreaterOrEqualsThan(offerTotalCost);
    }

    @Override
    public void charge(Money offerTotalCost, String title, Client client) {
        if (canAfford(offerTotalCost, client)) {
            if (client.getSaldo().isGreaterOrEqualsThan(offerTotalCost))
                client.setSaldo(client.getSaldo().substract(offerTotalCost));
            else {
                client.setDebt(offerTotalCost.substract(client.getSaldo()));
                client.setCreditLimit(client.getCreditLimit().substract(client.getDebt()));
                client.setSaldo(Money.ZERO_PL);
            }
        } else
            throw new IllegalStateException("Your count does not exist enough money");
    }

    @Override
    public void recharge(Money money, Client client) {
        if (client.getDebt().isGreaterThan(Money.ZERO_PL)) {
            if (money.isGreaterOrEqualsThan(client.getDebt())) {
                client.setSaldo(money.substract(client.getDebt()));
                client.setDebt(Money.ZERO_PL);
            } else
                client.setDebt(client.getDebt().substract(money));
        } else
            client.setSaldo(client.getSaldo().add(money));
    }
}