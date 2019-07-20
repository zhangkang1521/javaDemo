package org.zk;

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


}
