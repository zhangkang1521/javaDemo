package org.zk;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: zhangkang
 * @CreateTime: 2025-04-24 13:18
 */
@Slf4j
public class ConditionTest {

    static Lock lock = new ReentrantLock();

    static Condition condition = lock.newCondition();

    @Test
    @SneakyThrows
    public void test() {
        new Thread(new WaitTask()).start();
        Thread.sleep(10);
        new Thread(new NotifyTask()).start();
        System.in.read();
    }

    static class WaitTask implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                log.info("释放锁，等待");
                condition.await();
                log.info("被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class NotifyTask implements Runnable {
        @Override
        @SneakyThrows
        public void run() {
            lock.lock();
            try {
                Thread.sleep(1000);
                log.info("唤醒线程");
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }
}
