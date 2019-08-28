package org.zk.replaceParam;

/**
 * 以函数取代参数，如果函数可以通过其他途径获得参数值，那么它就不应该通过参数获得该值
 */
public class ReplaceParam {

    private int quantity;
    private int itemPrice;

    public ReplaceParam(int quantity, int itemPrice) {
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

    public double getPrice() {
        return disCountedPrice();
    }

    public int getBasePrice() {
        return quantity * itemPrice;
    }

    private int getDisCountLevel() {
        return quantity > 100 ? 2 : 1;
    }

    private double disCountedPrice() {
        if (getDisCountLevel() == 2) {
            return getBasePrice() * 0.1;
        } else {
            return getBasePrice() * 0.05;
        }
    }

}
