package org.zk.extractSubClass;

/**
 * JobItem类用于决定当地修车厂的工作报价
 */
public class JobItem {

    private int unitPrice;
    private int quantity;
    private Employee employee;
    private boolean isLabor;

    public JobItem(int unitPrice, int quantity, Employee employee, boolean isLabor) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.employee = employee;
        this.isLabor = isLabor;
    }

    public int getUnitPrice() {
        return isLabor ? employee.getRate() : unitPrice;
    }

    public int getTotalPrice() {
        return getUnitPrice() * quantity;
    }


}
