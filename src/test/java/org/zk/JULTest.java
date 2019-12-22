package org.zk;

import org.junit.Test;
//import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * java日志实现
 */
public class JULTest {

    static {
//        LogManager.getLogManager().reset();
//        SLF4JBridgeHandler.install();
    }

    @Test
    public void test1() {
        Logger logger = Logger.getLogger("test");
        logger.log(Level.INFO, "hello");
        logger.log(Level.WARNING, "hello");
    }
}
