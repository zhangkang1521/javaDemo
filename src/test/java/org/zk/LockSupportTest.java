package org.zk;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author: zhangkang
 * @CreateTime: 2025-04-30 11:02
 */
@Slf4j
public class LockSupportTest {

    @Test
    @SneakyThrows
    public void test() {
        // synchronized wait/notify
        // ReentrantLock await/signal
        // LockSupport park/unpark
        // 与wait,notify的区别是可以不用进入锁
        Thread thread = new Thread(() -> {
            log.info("start");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("park");
            LockSupport.park();
            log.info("un park");
        });

        thread.start();

        // unpark先执行也可以，因为是基于许可证
        Thread.sleep(1000);
        log.info("un park thread");
        LockSupport.unpark(thread);






        System.in.read();
    }


}
