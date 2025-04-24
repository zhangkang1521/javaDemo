package org.zk;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: zhangkang
 * @CreateTime: 2025-04-23 09:56
 */
@Slf4j
public class ReentrantLockTest {

    static Lock lock = new ReentrantLock();

    @Test
    @SneakyThrows
    public void test() {
        new Thread(new Task()).start();
        new Thread(new Task()).start();
        System.in.read();
    }

    static class Task implements Runnable {

        @Override
        @SneakyThrows
        public void run() {
            // tryLock 应该放到try外面，否则没有获取到锁的情况也释放锁，会报错
            if (lock.tryLock(1, TimeUnit.SECONDS)) {
                try {
                    log.info("get locked");
                    Thread.sleep(2000);
                    log.info("release lock");
                } finally {
                    log.info("unlock");
                    lock.unlock();
                }
            } else {
                log.info("get locked failed");
            }
        }
    }
}
