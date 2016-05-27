package pl.com.bottega.photostock.sales.model.users;

import pl.com.bottega.photostock.sales.model.deal.Money;

import java.util.UUID;


public class Client {

    private String firstName;
    private String secondName;
    private String address;
    private String number = UUID.randomUUID().toString();
    private Money creditLimit;
    private Money debt;
    private Money saldo;
    private boolean isActive = true;
    private Company company;

    private PaymentStrategy paymentStrategy = new WithoutCredit();

    public Client(String firstName, String secondName, String address, Money saldo) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.address = address;
        this.saldo = saldo;
        this.debt = new Money(0, saldo.getCurrency());
        this.creditLimit = new Money(0, saldo.getCurrency());
    }

    public Money getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Money creditLimit) {
        this.creditLimit = creditLimit;
    }

    public PaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public String getName() {
        return firstName + " " + secondName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean canAfford(Money offerTotalCost) {
        return paymentStrategy.canAfford(offerTotalCost, this);
    }

    public void charge(Money offerTotalCost, String title) {
        paymentStrategy.charge(offerTotalCost, title, this);
    }

    public void recharge(Money addMoney) {
        paymentStrategy.recharge(addMoney, this);
    }

    public Money getDebt() {
        return debt;
    }

    public void setDebt(Money debt) {
        this.debt = debt;
    }

    public Company getCompany() {
        return company;
    }

    public Money getSaldo() {
        return saldo;
    }

    public void setSaldo(Money saldo) {
        this.saldo = saldo;
    }

    public String getAddress() {
        return address;
}



    public String[] export() {
        String name = getName();
        String address = getAddress();
        String number = getNumber();
        String creditLimit = getCreditLimit().getPriceFraction();
        String debt = getDebt().getPriceFraction();
        String saldo = getSaldo().getPriceFraction();
        String isActive = String.valueOf(isActive());
        String company = "MyCompany"; //getCompany().getName();
        String status = "VIP";//getPaymentStrategy().getClass().getSimpleName();

        String[] result = {name, address, number, creditLimit, debt, saldo, isActive, company, status};
        return result;
    }

    public enum LanguageFormatter {PL, EN}

}