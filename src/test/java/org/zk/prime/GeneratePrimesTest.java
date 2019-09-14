package org.zk.prime;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class GeneratePrimesTest {

    @Test
    public void generatePrimes() {
        int[] a = GeneratePrimes.generatePrimes(10);
        Assert.assertEquals(4, a.length);
        Assert.assertEquals(2, a[0]);
        Assert.assertEquals(3, a[1]);
        Assert.assertEquals(5, a[2]);
        Assert.assertEquals(7, a[3]);
    }
}