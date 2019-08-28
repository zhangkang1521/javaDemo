package org.zk.replaceParam;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReplaceParamTest {

    @Test
    public void getPrice() {
        Assert.assertEquals(2.5, new ReplaceParam(5, 10).getPrice(), 0.0);
        Assert.assertEquals(500.0, new ReplaceParam(500, 10).getPrice(), 0.0);
    }
}