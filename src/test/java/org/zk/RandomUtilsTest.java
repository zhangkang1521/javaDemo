package org.zk;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

/**
 * Created by Administrator on 3/5/2018.
 */
public class RandomUtilsTest {

    @Test
    public void test1() {
        // 在指定字符串中生成长度为n的随机字符串
        System.out.println(RandomStringUtils.random(5, "abcdefghijk"));
        // 随机的2位数
        System.out.println(RandomStringUtils.randomNumeric(2));
    }
}
