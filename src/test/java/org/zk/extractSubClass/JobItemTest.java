package org.zk.extractSubClass;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class JobItemTest {

    private JobItem normalJobItem = new JobItem(5, 100, null, false);
    private JobItem laborJobItem = new JobItem(5, 100, new Employee(2), true);

    @Test
    public void getUnitPrice() {
        Assert.assertEquals(5, normalJobItem.getUnitPrice());
        Assert.assertEquals(2, laborJobItem.getUnitPrice());
    }

    @Test
    public void getTotalPrice() {
        Assert.assertEquals(500, normalJobItem.getTotalPrice());
        Assert.assertEquals(200, laborJobItem.getTotalPrice());
    }
}