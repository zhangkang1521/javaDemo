package org.zk.typeCodeSubclasses;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeTest {

    @Test
    public void calculateSalary() {
        Employee engine = new Engineer();
        Employee salesMan = new SalesMan();
        Employee manager = new Manager();
        Assert.assertEquals(engine.calculateSalary(2), 2000);
        Assert.assertEquals(salesMan.calculateSalary(2), 4200);
        Assert.assertEquals(manager.calculateSalary(1), 1000000);
    }
}