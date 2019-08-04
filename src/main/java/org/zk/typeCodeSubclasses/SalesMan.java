package org.zk.typeCodeSubclasses;

public class SalesMan extends Employee {

    public int calculateSalary(int day) {
        return 4000 + day * 100;
    }
}
