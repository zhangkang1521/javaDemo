package org.zk;

/**
 * Created by zhangkang on 2019/7/21.
 */
public class ChildrensPrice extends Price {

    public double getCharge(int daysRented) {
        double result = 1.5;
        if (daysRented > 3) {
            result += (daysRented - 3) * 1.5;
        }
        return result;
    }
}
