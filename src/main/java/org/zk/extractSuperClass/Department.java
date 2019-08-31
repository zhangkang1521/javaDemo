package org.zk.extractSuperClass;

import java.util.ArrayList;
import java.util.List;

public class Department extends Party {
    private List<Employee> staff = new ArrayList<>();

    public Department(String name) {
        super(name);
    }

    public List<Employee> getStaff() {
        return staff;
    }

    public void addStaff(Employee employee) {
        staff.add(employee);
    }

    public int getAnnualCost() {
        return staff.stream().mapToInt(Employee::getAnnualCost).sum();
    }

    public int getHeadCount() {
        return staff.size();
    }
}
