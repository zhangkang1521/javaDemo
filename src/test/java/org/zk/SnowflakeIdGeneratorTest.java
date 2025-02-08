package org.zk;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

public class SnowflakeIdGeneratorTest {

    @Test
    public void nextId() throws Exception {
        // workId从zk获取
        SnowflakeIdGenerator.instance.init(1L);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int j = 0; j < 10; j++) {
            executorService.submit(() -> {
                for (int i = 0; i < 2000; i++) {
                    Long id = SnowflakeIdGenerator.instance.nextId();
//            Thread.sleep(1);
//                    System.out.println(id);
                }
            });
        }

        Thread.sleep(10000);

    }
}