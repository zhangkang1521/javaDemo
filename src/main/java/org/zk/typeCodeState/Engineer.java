package org.zk.typeCodeState;

public class Engineer extends EmployeeType {
    @Override
    int calculateSalary(int day) {
        return 1000 * day;
    }
}
