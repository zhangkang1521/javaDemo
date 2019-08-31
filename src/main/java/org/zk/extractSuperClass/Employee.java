package org.zk.extractSuperClass;

public class Employee {

    private String name;
    private String id;
    private int annualCost;

    public Employee(String name, String id, int annualCost) {
        this.name = name;
        this.id = id;
        this.annualCost = annualCost;
    }

    public int getAnnualCost() {
        return annualCost;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
