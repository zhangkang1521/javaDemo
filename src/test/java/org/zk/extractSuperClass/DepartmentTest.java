package org.zk.extractSuperClass;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DepartmentTest {

    Department department = new Department("lvmama");

    @Before
    public void before() {
        Department publicService = new Department("publicService");
        Department kefu = new Department("kefu");
        Department search = new Department("search");
        Department finance = new Department("finance");
        Employee zk = new Employee("zk", "1", 100000);
        Employee zx = new Employee("zx", "2", 5000);
        Employee rubinwen = new Employee("rubinwen", "3", 200000);
        Employee renzeqing = new Employee("renzeqing", "4", 1000);
        department.addParty(publicService);
        department.addParty(kefu);
        publicService.addParty(search);
        publicService.addParty(finance);
        kefu.addParty(renzeqing);
        finance.addParty(zk);
        finance.addParty(zx);
        search.addParty(rubinwen);
    }

    @Test
    public void getName() {
        Assert.assertEquals("lvmama", department.getName());
    }

    @Test
    public void getTotalAnnualCost() {
        Assert.assertEquals(306000, department.getAnnualCost());
    }

    @Test
    public void getHeadCount() {
        Assert.assertEquals(4, department.getHeadCount());
    }
}