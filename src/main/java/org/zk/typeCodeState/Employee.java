package org.zk.typeCodeState;


public class Employee {
    private int type;
    static final int ENGINEER = 0;
    static final int SALESMAN = 1;
    static final int MANAGER = 2;

    Employee(int type) {
        this.type = type;
    }

    public int calculateSalary(int day) {
        // TODO 用状态模式取代类型码
        int result = 0;
        switch (type) {
            case ENGINEER : result = 1000 * day; break;
            case SALESMAN : result = 4000 + day * 100; break;
            case MANAGER : result = 1000000; break;
            default: throw new RuntimeException("unKnow employ type");
        }
        return result;
    }
}

