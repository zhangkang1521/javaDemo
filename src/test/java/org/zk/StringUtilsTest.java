package org.zk;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class StringUtilsTest {
    @Test
    public void testEquals() {
        System.out.println(StringUtils.equals("11", new String("11")));
    }

    @Test
    public void testJoin() {
        // 字符串拼接
        System.out.println(StringUtils.join(Arrays.asList("a", "b", "c"), ","));
    }

    @Test
    public void testCapitalize() {
        // 首字母大写
        System.out.println(StringUtils.capitalize("abcAB"));
        System.out.println(StringUtils.capitalize("Abc"));
    }

    @Test
    public void anyEmpty() {
        Assert.assertTrue(StringUtils.isAnyBlank("a", "b", ""));
        Assert.assertTrue(StringUtils.isAllBlank("", null));
    }
}
