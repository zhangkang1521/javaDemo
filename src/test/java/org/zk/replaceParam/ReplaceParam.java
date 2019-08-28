package org.zk.replaceParam;

/**
 * 以函数取代参数，如果函数可以通过其他途径获得参数值，那么它就不应该通过参数获得该值
 */
public class ReplaceParam {

    private int quantity = 10;
    private int itemPrice = 2;

    public ReplaceParam(int quantity, int itemPrice) {
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

    public double getPrice() {
        int basePrice = quantity * itemPrice;
        int disCountLevel;
        if (quantity > 100) {
            disCountLevel = 2;
        } else {
            disCountLevel = 1;
        }
        double finalPrice = disCountedPrice(basePrice, disCountLevel);
        return finalPrice;
    }

    private double disCountedPrice(int basePrice, int disCountLevel) {
        if (disCountLevel == 2) {
            return basePrice * 0.1;
        } else {
            return basePrice * 0.05;
        }
    }

}
