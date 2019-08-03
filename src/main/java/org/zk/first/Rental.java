package org.zk.first;

/**
 * Created by zhangkang on 2019/7/20.
 */
public class Rental {
    private Movie movie;
    private int daysRented;

    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }

    public Movie getMovie() {
        return movie;
    }


    public int getDaysRented() {
        return daysRented;
    }

    public double getCharge() {
        return getMovie().getCharge(daysRented);
    }

    public int getFrequentRenterPoints() {
        return getMovie().getFrequentRenterPoints(daysRented);
    }


}
