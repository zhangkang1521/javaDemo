package org.zk.extractSubClass;

public class LaborItem extends JobItem {

    private Employee employee;

    public LaborItem(int quantity, Employee employee) {
        super(0, quantity);
        this.employee = employee;
    }

    public int getUnitPrice() {
        return employee.getRate();
    }
}
