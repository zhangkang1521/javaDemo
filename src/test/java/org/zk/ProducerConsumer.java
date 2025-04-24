package org.zk;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: zhangkang
 * @CreateTime: 2025-04-24 13:36
 */
@Slf4j
public class ProducerConsumer {

    private static AtomicInteger counter = new AtomicInteger(0);

    private static final int MAX_SIZE = 10;

    private static List<Integer> list = new ArrayList<>(MAX_SIZE);

    private static Lock lock = new ReentrantLock();

    // 如果使用1个条件，会唤醒所有线程，可能导致无法生产或消费
    // 2个条件生产者生产了唤醒消费者，消费者消费了唤醒生产者

    /**
     * 缓冲区未满，生产者可以继续生产
     */
    private static Condition notFull = lock.newCondition();

    /**
     * 缓冲区未空的条件，消费者可以消费
     */
    private static Condition notEmpty = lock.newCondition();


    static class Producer implements Runnable {

        private int sleepTime;

        public Producer(int sleepTime) {
            this.sleepTime = sleepTime;
        }

        @Override
        @SneakyThrows
        public void run() {
            while (true) {
                lock.lock();
                try {
                    if (list.size() >= MAX_SIZE) {
                        log.info("队列已满，生产者等待...");
                        notFull.await();
                        log.info("生产者被唤醒");
                    }
                    Integer value = counter.incrementAndGet();
                    list.add(value);
                    log.info("生产：{}", value);
                    notEmpty.signal();
                    Thread.sleep(sleepTime);
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static class Consumer implements Runnable {

        private int sleepTime;

        public Consumer(int sleepTime) {
            this.sleepTime = sleepTime;
        }

        @Override
        @SneakyThrows
        public void run() {
            while (true) {
                lock.lock();
                try {
                    if (list.size() > 0) {
                        Integer value = list.remove(0);
                        log.info("消费：{}", value);
                        notFull.signal();
                        Thread.sleep(sleepTime);
                    } else {
                        log.info("消费者等待");
                        notEmpty.await();
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new Producer(500)).start();
            new Thread(new Consumer(500)).start();
        }
    }
}
