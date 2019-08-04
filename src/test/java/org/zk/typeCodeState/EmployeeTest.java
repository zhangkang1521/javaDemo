package org.zk.typeCodeState;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeTest {

    @Test
    public void calculateSalary() {
        Employee engine = new Employee(Employee.ENGINEER);
        Employee salesMan = new Employee(Employee.SALESMAN);
        Employee manager = new Employee(Employee.MANAGER);
        assertEquals(engine.calculateSalary(2), 2000);
        assertEquals(salesMan.calculateSalary(2), 4200);
        assertEquals(manager.calculateSalary(1), 1000000);
        // 工程师晋升为管理，薪资算法改变，这就是状态模式
        engine.setType(Employee.MANAGER);
        assertEquals(engine.calculateSalary(1), 1000000);
    }
}