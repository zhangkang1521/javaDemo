package org.zk;

/**
 * Created by zhangkang on 2019/7/21.
 */
public class RegularPrice extends Price {

    public double getCharge(int daysRented) {
        double result = 2;
        if (daysRented > 2) {
            result += (daysRented - 2) * 1.5;
        }
        return result;
    }
}
