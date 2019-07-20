package org.zk.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by zhangkang on 2019/7/20.
 */
public class FutureTest {
    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        Future f = exec.submit(new Callable<Object>() {
            public Integer call() throws InterruptedException {
                System.out.println("call");
                Thread.sleep(2000);
                return 1;
            }
        });
        System.out.println(f.get());
        System.out.println("end");

    }
}
