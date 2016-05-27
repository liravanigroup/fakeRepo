package pl.com.bottega.commons.math.model;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-26.
 */

public class FractionTest {

    public static final Fraction WRONG_FRACTION = new Fraction(12005, 1000);
    public static final Fraction ONE_FRACTION = new Fraction(1, 1);
    public static final Fraction SIMPLE_FRACTION = new Fraction(1, 2);
    public static final Fraction ZERO_FRACTION = new Fraction(0, 1000);
    public static final Fraction WHOLE_FRACTION = new Fraction(2);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldThrowIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Denominator can not be zero");
        Fraction zeroFraction = new Fraction(1, 0);
    }

    @Test
    public void shouldAddFractions() {
        Fraction result = ONE_FRACTION.add(WHOLE_FRACTION);
        Fraction expectedFraction = new Fraction(3, 1);
        Assert.assertEquals(expectedFraction, result);
    }

    @Test
    public void shouldReverseFraction() {
        Assert.assertEquals(new Fraction(2, 1), SIMPLE_FRACTION.reverse());
    }

    @Test
    public void shouldThrownIllegalStateExceptionIncorrectReverse() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Can not reverse zero");
        ZERO_FRACTION.reverse();
    }

    @Test
    public void shouldGetDenominator() {
        Assert.assertEquals(1, ONE_FRACTION.getDenominator());
    }

    @Test
    public void shouldGetNominator() {
        Assert.assertEquals(1, ONE_FRACTION.getDenominator());
    }

    @Test
    public void shouldGetWholePartOfFraction() {
        Assert.assertEquals(12, WRONG_FRACTION.getWholePartOfFraction());
    }

    @Test
    public void shouldGetZeroWholePart() {
        Assert.assertEquals(0, ZERO_FRACTION.getWholePartOfFraction());
    }

    @Test
    public void shouldGetNominatorWithoutWholePart() {
        Assert.assertEquals(5, WRONG_FRACTION.getNominatorWithoutWholePart());
    }

    @Test
    public void shouldGetNominatorWithoutWholePart2() {
        Assert.assertEquals(1, SIMPLE_FRACTION.getNominatorWithoutWholePart());
    }

    @Test
    public void shouldGetNominatorWithoutWholePart3() {
        Assert.assertEquals(0, ONE_FRACTION.getNominatorWithoutWholePart());
    }


    @Test
    public void shouldGetSimplifiedNominator() {
        Assert.assertEquals(1, WRONG_FRACTION.getSimplifiedNominator());
    }

    @Test
    public void shouldGetSimplifiedDenominator() {
        Assert.assertEquals(200, WRONG_FRACTION.getSimplifiedDenominator());
    }

    @Test
    public void shouldBeGreaterOrEqualsThan() {
        Assert.assertTrue(ONE_FRACTION.isGreaterOrEqualsThan(SIMPLE_FRACTION));
    }

    @Test
    public void shouldNotBeGreaterOrEqualsThan() {
        Assert.assertFalse(SIMPLE_FRACTION.isGreaterOrEqualsThan(ONE_FRACTION));
    }

    @Test
    public void shouldBeLessOrEqualsThan() {
        Assert.assertTrue(SIMPLE_FRACTION.isLessOrEqualsThan(ONE_FRACTION));
    }

    @Test
    public void shouldNotBeLessOrEqualsThan() {
        Assert.assertFalse(ONE_FRACTION.isLessOrEqualsThan(SIMPLE_FRACTION));
    }

    @Test
    public void shouldBeLessThan() {
        Assert.assertTrue(SIMPLE_FRACTION.isLessThan(ONE_FRACTION));
    }

    @Test
    public void shouldBeGreaterThan() {
    Assert.assertTrue(ONE_FRACTION.isGreaterThan(SIMPLE_FRACTION));
    }

    @Test
    public void substract() {
    Assert.assertEquals(SIMPLE_FRACTION, ONE_FRACTION.substract(SIMPLE_FRACTION));
    }

    @Test
    public void shouldSubstractFractions() {
        Assert.assertTrue(SIMPLE_FRACTION.substract(ONE_FRACTION).isLessThan(ZERO_FRACTION));
    }

}