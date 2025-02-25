package org.zk;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
@Slf4j
public class FutureTaskTest {

    @Test
    @SneakyThrows
    public void test1() {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            log.info("hello");
            Thread.sleep(1000);
            return "hello";
        });
        // 执行futureTask的run方法; run方法中执行call方法，拿到结果设置到outcome中
        new Thread(futureTask).start();
        String result = futureTask.get();
        log.info("result:{}", result);
    }
}
