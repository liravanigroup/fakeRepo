package pl.com.bottega.photostock.sales.model.common;

/**
 * Sergej Povzaniuk
 * 2016-03-29.
 */
public class MathUtilits {



    private static int getAbsValueOfNumber(int Number) {
        return Math.abs(Number);
    }

    public static int getSymbolCount(int Number) {
        Number = getAbsValueOfNumber(Number);
        int count = 0;
        if (Number < 10 || Number == 0) {
            count = 1;
        } else if (Number < 100) {
            count = 2;
        } else if (Number < 1000) {
            count = 3;
        } else if (Number < 10000) {
            count = 4;
        } else if (Number < 100000) {
            count = 5;
        } else if (Number < 1000000) {
            count = 6;
        } else if (Number < 10000000) {
            count = 7;
        } else if (Number < 100000000) {
            count = 8;
        } else if (Number < 2147483647) {
            count = 9;
        }
        return count;
    }




}
