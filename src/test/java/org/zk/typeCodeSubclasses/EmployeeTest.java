package org.zk.typeCodeSubclasses;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeTest {

    @Test
    public void calculateSalary() {
        Employee engine = new Employee(Employee.ENGINEER);
        Employee salesMan = new Employee(Employee.SALESMAN);
        Employee manager = new Employee(Employee.MANAGER);
        Assert.assertEquals(engine.calculateSalary(2), 2000);
        Assert.assertEquals(salesMan.calculateSalary(2), 4200);
        Assert.assertEquals(manager.calculateSalary(1), 1000000);
    }
}