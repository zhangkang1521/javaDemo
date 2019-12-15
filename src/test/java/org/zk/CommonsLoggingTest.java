package org.zk;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class CommonsLoggingTest {

    // commons-logging 是一个日志接口，会按如下优先顺序使用具体实现
    // "org.apache.commons.logging.impl.Log4JLogger",
    // "org.apache.commons.logging.impl.Jdk14Logger",
    // "org.apache.commons.logging.impl.Jdk13LumberjackLogger",
    // "org.apache.commons.logging.impl.SimpleLog"};
    Log log = LogFactory.getLog(CommonsLoggingTest.class);

    @Test
    public void test1() {
        System.out.println(log.getClass());
        log.info("hello");
    }
}
