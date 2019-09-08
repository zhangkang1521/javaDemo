package org.zk;


import com.alibaba.fastjson.JSON;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void test1() {
        // https://nosec.org/home/detail/2933.html
        JSON.parse("{\"a\":\"\\x");
    }
}
