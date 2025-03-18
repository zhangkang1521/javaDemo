package org.zk;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangkang
 * @date 2025/3/18 17:03
 */
@Slf4j
public class PlusTest {


    @Test
    @SneakyThrows
    public void test() {

        Counter count = new Counter();

        CountDownLatch latch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                count.add(10000);
                latch.countDown();
            }).start();
        }

        if (latch.await(2, TimeUnit.MINUTES)) {
            log.info("count {}", count.getCount());
        }
    }

    static class Counter {

        private static int count = 0;

        /**
         * 不加synchronized, 会出现数据不一致问题
         * @param step
         */
        public synchronized void add(int step) {
            for (int j = 0; j < step; j++) {
                count++;
            }
        }

        public int getCount() {
            return count;
        }
    }
}
