package org.zk.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangkang on 2019/4/13.
 */
public class CachedThreadPool {
    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            es.execute(new LiftOff());
        }
        es.shutdown();
    }
}
