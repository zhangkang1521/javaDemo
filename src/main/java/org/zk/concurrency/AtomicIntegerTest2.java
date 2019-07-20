package org.zk.concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhangkang on 2019/7/14.
 */
public class AtomicIntegerTest2 {

    static AtomicInteger a = new AtomicInteger(0);

    static class Task implements Runnable {



        @Override
        public void run() {
            int old = a.get();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Random rand = new Random();
            boolean set = a.compareAndSet(old, rand.nextInt());
            if (!set) {
                System.out.println("current error");
            }
        }
    }


    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Task());
        }
    }
}
