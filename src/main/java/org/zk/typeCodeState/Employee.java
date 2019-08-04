package org.zk.typeCodeState;


public class Employee {
    private EmployeeType type;
    static final int ENGINEER = 0;
    static final int SALESMAN = 1;
    static final int MANAGER = 2;

    Employee(int type) {
       setType(type);
    }

    public void setType(int type) {
        switch (type) {
            case ENGINEER : this.type = new Engineer(); break;
            case SALESMAN : this.type = new SalesMan(); break;
            case MANAGER : this.type = new Manager(); break;
            default: throw new RuntimeException("unKnow employ type");
        }
    }

    public int calculateSalary(int day) {
       return type.calculateSalary(day);
    }
}

