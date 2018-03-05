package org.zk;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

/**
 * Created by Administrator on 3/5/2018.
 */
public class NumberUtilsTest {

    @Test
    public void test1() {
        // 判断是否是数字
        System.out.println(NumberUtils.isCreatable("12.2"));
        // 判断是否是整数
        System.out.println(NumberUtils.isDigits("12.2"));
    }
}
