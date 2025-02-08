package org.zk;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;
import org.junit.Test;

/**
 * @author zhangkang
 * @date 2025/2/8 10:44
 */
@Slf4j
public class InterProcessMutexTest {

    @Test
    @SneakyThrows
    public void test() {
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", new RetryNTimes(1, 1000));
        client.start();
        InterProcessMutex mutex = new InterProcessMutex(client, "/mutex");

        new Thread(new LockTask(mutex)).start();
        new Thread(new LockTask(mutex)).start();

        Thread.sleep(5000);

        client.close();
    }

    static class LockTask implements Runnable {

        private InterProcessMutex mutex;

        public LockTask(InterProcessMutex mutex) {
            this.mutex = mutex;
        }

        @Override
        @SneakyThrows
        public void run() {
            log.info("acquire start");
            mutex.acquire();
            log.info("acquire success");
            Thread.sleep(1000);
            log.info("release mutex");
            mutex.release();
        }
    }
}
