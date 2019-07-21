package org.zk;

/**
 * Created by zhangkang on 2019/7/20.
 */
public class Movie {

    public static final int CHILDRENS = 2;
    public static final int REGULAR = 0;
    public static final int NEW_RELEASE = 1;

    private String title;
    private Price price;

    public Movie(String title, int priceCode) {
        this.title = title;
        this.setPriceCode(priceCode);
    }

    public String getTitle() {
        return title;
    }


    public void setPriceCode(int priceCode) {
        switch (priceCode) {
            case Movie.REGULAR:
                this.price = new RegularPrice();
                break;
            case Movie.NEW_RELEASE:
                this.price = new NewReleasePrice();
                break;
            case Movie.CHILDRENS:
                this.price = new ChildrensPrice();
                break;
            default:
                throw new IllegalArgumentException("incorrect price code");
        }
    }

    public double getCharge(int daysRented) {
        return price.getCharge(daysRented);
    }

    public int getFrequentRenterPoints(int daysRented) {
        return price.getFrequentRenterPoints(daysRented);
    }
}
