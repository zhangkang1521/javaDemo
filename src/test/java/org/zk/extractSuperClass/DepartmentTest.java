package org.zk.extractSuperClass;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentTest {

    Department department = new Department("finance");

    @Before
    public void before() {
        department.addStaff(new Employee("zk", "1", 100000));
        department.addStaff(new Employee("zx", "2", 5000));
    }

    @Test
    public void getName() {
        Assert.assertEquals("finance", department.getName());
    }

    @Test
    public void getTotalAnnualCost() {
        Assert.assertEquals(105000, department.getTotalAnnualCost());
    }

    @Test
    public void getHeadCount() {
        Assert.assertEquals(2, department.getHeadCount());
    }
}