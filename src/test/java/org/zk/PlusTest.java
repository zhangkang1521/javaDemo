package org.zk;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.zk.unsafe.UnsafeUtils;
import sun.misc.Unsafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
        // 在低并发场景下，synchronized 和 CAS 的性能差异不大。
        // 在高并发场景下，CAS 的性能通常优于 synchronized，因为 CAS 避免了锁的竞争。
        synchronizedAdd();
        casAdd();
    }



//    @Test
    @SneakyThrows
    public void synchronizedAdd() {

        long start = System.currentTimeMillis();

        Counter count = new Counter();

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

    static class Counter {

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
}
