package org.zk.jol;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

@Slf4j
public class SynchronizedTest {




    @Test
    @SneakyThrows
    public void biased() {
        // https://www.cnblogs.com/katsu2017/p/12610002.html

        Thread.sleep(5000); //等待jvm开启偏向锁
        Object obj = new Object();
        // 匿名偏向
        log.info("匿名偏向: {}", ClassLayout.parseInstance(obj).toPrintable());
        synchronized (obj) {
            // 以run的方式启动，如果用debug启动直接升级为轻量级锁
            // 偏向main线程
            log.info("偏向: {}", ClassLayout.parseInstance(obj).toPrintable());
        }
    }

    @Test
    @SneakyThrows
    public void light() {
        Thread.sleep(5000);
        Object o = new Object();
        synchronized (o) {
            log.info("偏向锁:{}", ClassLayout.parseInstance(o).toPrintable());
        }
        for (int i = 0; i < 1; i++) {
            Thread t = new Thread(() -> {
                synchronized (o){
                    log.info("轻量级锁：{}", ClassLayout.parseInstance(o).toPrintable());
                }
            });
            t.start();
        }
        Thread.sleep(1000);
    }

    @Test
    @SneakyThrows
    public void weight() {
        Thread.sleep(5000);
        Object o = new Object();
        synchronized (o) {
            log.info("偏向锁:{}", ClassLayout.parseInstance(o).toPrintable());
        }
        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(() -> {
                synchronized (o){
                    log.info("重量级锁：{}", ClassLayout.parseInstance(o).toPrintable());
                }
            });
            t.start();
        }
        Thread.sleep(1000);
    }
}
