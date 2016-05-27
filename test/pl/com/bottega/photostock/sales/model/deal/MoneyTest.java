package pl.com.bottega.photostock.sales.model.deal;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.com.bottega.commons.math.model.Fraction;

import static pl.com.bottega.photostock.sales.model.deal.Money.Currency.PLN;
import static pl.com.bottega.photostock.sales.model.deal.Money.Currency.USD;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-04.
 */

public class MoneyTest {

    private final static Money INITIAL_FRACTION_MONEY = new Money(140.56605, PLN);
    private final static Money INITIAL_FRACTION_MONEY_CLONE = new Money(140.56605, PLN);

    private final static Money INITIAL_MONEY_FULL_FRACTION_PART = new Money(10, 100, PLN);
    private final static Money INITIAL_MONEY_THE_SAME_LIKE_FULL_FRACTION_PART = new Money(11, PLN);


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldCreateMoney() {
        Money money = new Money(140.56605, PLN);
        Money money2 = new Money(10, PLN);
        Money money3 = new Money(150, 99, PLN);

        //TODO
    }

    @Test
    public void shouldCreateMoneyWithFullCents() {
        Assert.assertEquals(INITIAL_MONEY_FULL_FRACTION_PART, INITIAL_MONEY_THE_SAME_LIKE_FULL_FRACTION_PART);
    }


    @Test
    public void shouldCompareMoney() {
        Assert.assertEquals(INITIAL_FRACTION_MONEY, INITIAL_FRACTION_MONEY_CLONE);
    }

    @Test
    public void shouldGetDifferentMoney() {
        Money money = new Money(100, PLN);
        Money money2 = new Money(102, PLN);
        double result = money.getPercentDifferent(money2);
        Assert.assertEquals(2.0, result, 0);
    }

    @Test
    public void shouldCompareGraterOfEqualsMoney() {
        Money money = new Money(140.56700, PLN);
        Money money2 = new Money(140.56605, PLN);
        Assert.assertTrue(money.isGreaterOrEqualsThan(money2));
    }

    @Test
    public void shouldCompareGraterMoney() {
        Money money = new Money(140.56700, PLN);
        Money money2 = new Money(140.56605, PLN);
        Assert.assertTrue(money.isGreaterThan(money2));
    }

    @Test
    public void shouldCompareLessOrEqualsMoney() {
        Money money = new Money(140.56700, PLN);
        Money money2 = new Money(140.56605, PLN);
        Assert.assertTrue(money2.isLessOrEqualsThan(money));
    }

    @Test
    public void shouldCompareLessMoney() {
        Money money = new Money(140.56700, PLN);
        Money money2 = new Money(140.56605, PLN);
        Assert.assertTrue(money2.isLessThan(money));
    }

    @Test
    public void shouldCompareHashCode() {
        Money money = new Money(140.56605, PLN);
        Money money2 = new Money(140.56605, PLN);
        Assert.assertEquals(money.hashCode(), money2.hashCode());
    }

    @Test
    public void shouldAddMoney() {
        Money money = new Money(120.566, PLN);
        Money money2 = new Money(140.56605, PLN);
        Money money3 = new Money(261.13205, PLN);
        Assert.assertEquals(money2.add(money), money3);
    }

    @Test
    public void shouldThrowIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Bad value! Permissible value should be greater than 0");
        Money money = new Money(-10, PLN);
    }

    @Test
    public void shouldThrowIllegalArgumentException2() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Bad value! Permissible value should be greater than 0 and cents should be less 100");
        Money money = new Money(25, 101, PLN);
    }

    @Test
    public void shouldThrowIllegalArgumentException3() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Currency is null");
        Money money = new Money(25, 95, null);
    }

    @Test
    public void shouldThrowIllegalArgumentException4() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Forbidden! Different currency!");
        Money moneyPLN = new Money(25, 25, PLN);
        Money moneyUSD = new Money(25, 25, USD);
        Money money = moneyPLN.add(moneyUSD);

    }

    @Test
    public void shouldSubstractMoney() {
        Money money = new Money(120.566, PLN);
        Money money2 = new Money(140.56605, PLN);
        Money money3 = new Money(20.00005, PLN);
        Assert.assertEquals(money2.substract(money), money3);
    }

    @Test
    public void shouldMultipleMoney() {
        Money money = new Money(10, PLN);
        Money money3 = new Money(20, PLN);
        Assert.assertEquals(money.multiple(2), money3);
    }

    @Test
    public void shouldMultipleMoneyDouble() {
        Money money = new Money(120.566, PLN);
        Money money3 = new Money(1507.075, PLN);
        Assert.assertEquals(money.multiple(12.5), money3);
    }

    @Test
    public void shouldConvertMoneyToCents1() {
        Money money = new Money(26, 15, USD);
        Assert.assertEquals(new Money(26.15, USD), new Money(new Fraction(money.getPriceFraction()), USD));
    }

    @Test
    public void shouldConvertMoneyToCents2() {
        Money money = new Money(12.0000000005, USD);
        Assert.assertEquals(new Money(12.0000000005, USD), new Money(new Fraction(money.getPriceFraction()), USD));
    }

    @Test
    public void shouldConvertMoneyToCents3() {
        Money money = new Money(26);
        Assert.assertEquals(new Money(26), new Money(new Fraction(money.getPriceFraction()), PLN));
    }

}