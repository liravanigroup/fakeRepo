package pl.com.bottega.photostock.sales.model.common;

/**
 * Sergej Povzaniuk
 * 2016-03-30.
 */
public class StringUtilites {

    public static String buildSymbolLine(int lenght, String symbol) {
        if (lenght == 0) {
            return "";
        } else {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < lenght; i++)
                s.append(symbol);
            return s.toString();
        }
    }

}
