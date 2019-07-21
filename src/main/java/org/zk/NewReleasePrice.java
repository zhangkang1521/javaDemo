package org.zk;

/**
 * Created by zhangkang on 2019/7/21.
 */
public class NewReleasePrice extends Price {


    @Override
    double getCharge(int daysRented) {
        return daysRented * 3;
    }

    public int getFrequentRenterPoints(int daysRented) {
         return daysRented > 1 ? 2 : 1;
    }
}
