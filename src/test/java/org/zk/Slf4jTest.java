package org.zk;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jTest {

    // 会查找classpath下 org/slf4j/impl/StaticLoggerBinder.class
    // 找不到，找到多个会报错
    Logger logger = LoggerFactory.getLogger(Slf4jTest.class);

    @Test
    public void test1() {
        logger.info("hello,slf4j");
        logger.warn("hello,slf4j");
    }
}
