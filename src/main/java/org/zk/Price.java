package org.zk;

/**
 * Created by zhangkang on 2019/7/21.
 */
public abstract class Price {
//    abstract int getPriceCode();
    abstract double getCharge(int daysRented);

    public int getFrequentRenterPoints(int daysRented) {
       return 1;
    }
}
