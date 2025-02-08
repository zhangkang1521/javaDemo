package org.zk;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ZkLockerTest {

    @Test
    @SneakyThrows
    public void tryLock() {
        new Thread(new TestThread()).start();
        new Thread(new TestThread()).start();
        Thread.sleep(5000);
    }

    @Slf4j
    static class TestThread implements Runnable {

        ZkLocker zkLocker = new ZkLocker();

        @Override
        @SneakyThrows
        public void run() {
            boolean locked = zkLocker.tryLock("/lock/test");
            boolean locked2 = zkLocker.tryLock("/lock/test");
            log.info("locked:{} locked2:{}", locked, locked2);
            TimeUnit.SECONDS.sleep(1);
            zkLocker.unlock();
            zkLocker.unlock();

            zkLocker.destroy();
        }
    }
}