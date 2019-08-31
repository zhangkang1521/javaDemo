package org.zk.extractSuperClass;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeTest {

    Employee employee = new Employee("zk", "1", 100000);

    @Test
    public void getAnnualCost() {
        Assert.assertEquals(100000, employee.getAnnualCost());
    }

    @Test
    public void getName() {
        Assert.assertEquals("zk", employee.getName());
    }

    @Test
    public void getId() {
        Assert.assertEquals("1", employee.getId());
    }
}