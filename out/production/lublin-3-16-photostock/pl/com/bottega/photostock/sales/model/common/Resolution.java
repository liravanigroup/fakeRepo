package pl.com.bottega.photostock.sales.model.common;

import pl.com.bottega.photostock.sales.model.deal.Money;

import java.util.ArrayList;
import java.util.List;

/**
 * Sergej Povzaniuk
 * 2016-03-30.
 */
public class Resolution implements Cloneable{

    private int widthImage;
    private int heightImage;
    private String nameResolution;
    private Money price;
    private boolean isAviable;

    public Resolution(int widthImage, int heightImage, Money price) {
        this.widthImage = widthImage;
        this.heightImage = heightImage;
        this.price = price;
        this.isAviable = true;
        this.nameResolution = getResolutionsName(widthImage, heightImage);

    }

    @Override
    public Resolution clone(){
        return new Resolution(widthImage,heightImage,price);
    }

    public boolean isAvialable() {
        return isAviable;
    }

    public void setAviable(boolean aviable) {
        isAviable = aviable;
    }

    public static int[] resizeTo3XL(int maxWidth, int maxHeight) {
        int[] sizes = new int[2];
        sizes[0] = 7680; //resizedWieght
        sizes[1] = 7680 * maxHeight / maxWidth; //resizedHeight
        return sizes;
    }

    public static int[] resizeTo2XL(int maxWidth, int maxHeight) {
        int[] sizes = new int[2];
        sizes[0] = 5120; //resizedWieght
        sizes[1] = 5120 * maxHeight / maxWidth; //resizedHeight
        return sizes;
    }

    public static int[] resizeToXL(int maxWidth, int maxHeight) {
        int[] sizes = new int[2];
        sizes[0] = 4128; //resizedWieght
        sizes[1] = 4128 * maxHeight / maxWidth; //resizedHeight
        return sizes;
    }

    public static int[] resizeToL(int maxWidth, int maxHeight) {
        int[] sizes = new int[2];
        sizes[0] = 2560; //resizedWieght
        sizes[1] = 2560 * maxHeight / maxWidth; //resizedHeight
        return sizes;
    }

    public static int[] resizeToM(int maxWidth, int maxHeight) {
        int[] sizes = new int[2];
        sizes[0] = 1600; //resizedWieght
        sizes[1] = 1600 * maxHeight / maxWidth; //resizedHeight
        return sizes;
    }

    public static int[] resizeToS(int maxWidth, int maxHeight) {
        int[] sizes = new int[2];
        sizes[0] = 848; //resizedWieght
        sizes[1] = 848 * maxHeight / maxWidth; //resizedHeight
        return sizes;
    }

    private static int getMaxSize(int width, int height) {
        int size = 0;
        if (width > height) {
            size = width;
        } else {
            size = height;
        }
        return size;
    }

    public static List<Resolution> getResolutionsList(Resolution maxResolution, String[] manualPrices) {
        List<Resolution> resolutions = new ArrayList<Resolution>();
        String nameOfMaxResolution = maxResolution.getNameResolution();

        int width = maxResolution.getWidthImage();
        int height = maxResolution.getHeightImage();

        for (String s : manualPrices) {
            String[] manualParam = s.split("x");
            double manualPrice = Double.parseDouble(manualParam[1]);

            switch (manualParam[0]) {
                case "S":
                    int[] sm = resizeToS(width,height);
                    resolutions.add(new Resolution(sm[0],sm[1],new Money(manualPrice)));
                    break;
                case "M":
                    int[] m = resizeToM(width,height);
                    resolutions.add(new Resolution(m[0],m[1],new Money(manualPrice)));
                    break;
                case "L":
                    int[] l = resizeToL(width,height);
                    resolutions.add(new Resolution(l[0],l[1],new Money(manualPrice)));
                    break;
                case "XL":
                    int[] xl = resizeToXL(width,height);
                    resolutions.add(new Resolution(xl[0],xl[1],new Money(manualPrice)));
                    break;
                case "XXL":
                    int[] xxl = resizeTo2XL(width,height);
                    resolutions.add(new Resolution(xxl[0],xxl[1],new Money(manualPrice)));
                    break;
                case "XXXL":
                    int[] xxxl = resizeTo3XL(width,height);
                    resolutions.add(new Resolution(xxxl[0],xxxl[1],new Money(manualPrice)));
                    break;
            }
        }
       return resolutions;
    }

    private static String getResolutionsName(int width, int height) {
        int size = getMaxSize(width, height);
        if (size < 8000 && size >= 7680)
            return "XXXL";
        else if (size >= 5120 && size < 7680)
            return "XXL";
        else if (size >= 4128 && size < 5120)
            return "XL";
        else if (size >= 2560 && size < 4128)
            return "L";
        else if (size >= 1600 && size < 2560)
            return "M";
        else if (size >= 848 && size < 1600)
            return "S";
        else
            return "";
    }

    public Money getPrice() {
        return price;
    }

    public String getNameResolution() {
        return nameResolution;
    }

    public int getWidthImage() {
        return widthImage;
    }

    public int getHeightImage() {
        return heightImage;
    }
}
