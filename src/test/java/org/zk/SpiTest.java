package org.zk;

import org.junit.Test;

import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

public class SpiTest {

    @Test
    public void test1() {
        ServiceLoader<Driver> loadedDrivers = ServiceLoader.load(Driver.class);
        Iterator<Driver> driversIterator = loadedDrivers.iterator();
        while(driversIterator.hasNext()) {
            System.out.println(driversIterator.next());
        }
    }

    @Test
    public void testMyDriver() {
        ServiceLoader<MyDriver> drivers = ServiceLoader.load(MyDriver.class);
        for (MyDriver driver : drivers) {
            System.out.println(driver.getClass().getName());
        }
    }
}
