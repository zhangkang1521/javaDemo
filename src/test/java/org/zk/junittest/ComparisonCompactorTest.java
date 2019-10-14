package org.zk.junittest;

import junit.framework.ComparisonCompactor;
import org.junit.Assert;
import org.junit.Test;

public class ComparisonCompactorTest {

    @Test
    public void test() {
        String failure = new ComparisonCompactor(3, "aab123", "aaxxx123").compact(null);
        System.out.println(failure);
//        Assert.assertTrue("expected:<[b]> but was:<[c]>".equals(failure));
    }
}
