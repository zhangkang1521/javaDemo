package org.zk.extractSuperClass;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private String name;
    private List<Employee> staff = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Employee> getStaff() {
        return staff;
    }

    public void addStaff(Employee employee) {
        staff.add(employee);
    }

    public int getTotalAnnualCost() {
        return staff.stream().mapToInt(Employee::getAnnualCost).sum();
    }

    public int getHeadCount() {
        return staff.size();
    }
}
