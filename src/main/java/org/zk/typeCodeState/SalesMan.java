package org.zk.typeCodeState;

public class SalesMan extends EmployeeType {
    @Override
    int calculateSalary(int day) {
        return 4000 + day * 100;
    }
}
