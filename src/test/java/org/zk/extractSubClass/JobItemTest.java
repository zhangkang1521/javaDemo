package org.zk.extractSubClass;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class JobItemTest {

    private JobItem normalJobItem = new JobItem(5, 100);
    private JobItem laborJobItem = new LaborItem(100, new Employee(2));

    @Test
    public void getUnitPrice() {
        assertEquals(5, normalJobItem.getUnitPrice());
        assertEquals(2, laborJobItem.getUnitPrice());
    }

    @Test
    public void getTotalPrice() {
        assertEquals(500, normalJobItem.getTotalPrice());
        assertEquals(200, laborJobItem.getTotalPrice());
    }
}