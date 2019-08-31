package org.zk.extractSubClass;

/**
 * JobItem类用于决定当地修车厂的工作报价
 */
public class JobItem {

    private int unitPrice;
    private int quantity;


    public JobItem(int unitPrice, int quantity) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }


    public int getUnitPrice() {
        return unitPrice;
    }

    public int getTotalPrice() {
        return getUnitPrice() * quantity;
    }


}
