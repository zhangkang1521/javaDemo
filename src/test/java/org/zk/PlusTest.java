package org.zk;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.zk.unsafe.UnsafeUtils;
import sun.misc.Unsafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangkang
 * @date 2025/3/18 17:03
 */
@Slf4j
public class PlusTest {

    public static final int THREAD = 10;
    public static final int STEP = 2000000;

    @Test
    public void test() {
        // 在低并发场景下，synchronized cas ReentrantLock 的性能差异不大。
        // 在高并发场景下，synchronized性能较差，因为使用了重量级锁
        synchronizedAdd();
        reentrantAdd();
        casAdd();
        // 高并发下，longAdder性能更好
        longAdder();
    }

    @Test
    @SneakyThrows
    public void longAdder() {

        long start = System.currentTimeMillis();

        LongAdder count = new LongAdder();

        CountDownLatch latch = new CountDownLatch(THREAD);

        for (int i = 0; i < THREAD; i++) {
            new Thread(() -> {
                for (int j = 0; j < STEP; j++) {
                    count.increment();
                }
                latch.countDown();
            }).start();
        }

        if (latch.await(2, TimeUnit.MINUTES)) {
            log.info("longAdder count {}", count.sum());
        }

        log.info("longAdder 耗时 {}", System.currentTimeMillis() - start);
    }



//    @Test
    @SneakyThrows
    public void synchronizedAdd() {

        long start = System.currentTimeMillis();

        SynchronizedCounter count = new SynchronizedCounter();

        CountDownLatch latch = new CountDownLatch(THREAD);

        for (int i = 0; i < THREAD; i++) {
            new Thread(() -> {
                for (int j = 0; j < STEP; j++) {
                    count.increment();
                }
                latch.countDown();
            }).start();
        }

        if (latch.await(2, TimeUnit.MINUTES)) {
            log.info("synchronized count {}", count.getCount());
        }

        log.info("synchronized 耗时 {}", System.currentTimeMillis() - start);
    }

    @SneakyThrows
    public void reentrantAdd() {

        long start = System.currentTimeMillis();

        ReentrantCounter count = new ReentrantCounter();

        CountDownLatch latch = new CountDownLatch(THREAD);

        for (int i = 0; i < THREAD; i++) {
            new Thread(() -> {
                for (int j = 0; j < STEP; j++) {
                    count.increment();
                }
                latch.countDown();
            }).start();
        }

        if (latch.await(2, TimeUnit.MINUTES)) {
            log.info("reentrant count {}", count.getCount());
        }

        log.info("reentrant 耗时 {}", System.currentTimeMillis() - start);
    }

//    @Test
    @SneakyThrows
    public void casAdd() {
        long start = System.currentTimeMillis();

        AtomicCounter count = new AtomicCounter();

        CountDownLatch latch = new CountDownLatch(THREAD);

        for (int i = 0; i < THREAD; i++) {
            new Thread(() -> {
                for (int j = 0; j < STEP; j++) {
                    count.increment();
                }
                latch.countDown();
            }).start();
        }

        if (latch.await(2, TimeUnit.MINUTES)) {
            log.info("cas count {}", count.getCount());
        }

        log.info("cas 耗时 {}", System.currentTimeMillis() - start);
    }

    static class SynchronizedCounter {

        private int count = 0;

        /**
         * 不加synchronized, 会出现数据不一致问题
         */
        public synchronized void increment() {
            count++;
        }

        public int getCount() {
            return count;
        }
    }

    static class AtomicCounter {

        private int count = 0;

        private static long offset;

        private static Unsafe unsafe = UnsafeUtils.getUnsafe();

        static {
            try {
                offset = unsafe.objectFieldOffset(AtomicCounter.class.getDeclaredField("count"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void increment() {
            // 这种写法有问题，count可能已经过期
//            unsafe.compareAndSwapInt(this, offset, count, count + 1);
            // 可改下成如下方式
//            int current;
//            do {
//                current = unsafe.getIntVolatile(this, offset);
//            } while (!unsafe.compareAndSwapInt(this, offset, current, current + 1));
            unsafe.getAndAddInt(this, offset, 1);
        }

        public int getCount() {
            return count;
        }
    }

    static Lock lock = new ReentrantLock();

    static class ReentrantCounter {

        private int count = 0;

        public void increment() {
            lock.lock();
            try {
                count++;
            } finally {
                lock.unlock();
            }
        }

        public int getCount() {
            return count;
        }
    }
}
