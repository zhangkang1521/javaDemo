package org.zk.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangkang on 2019/6/16.
 */

class TaskPortion implements Runnable {

    private CountDownLatch countDownLatch;

    public TaskPortion(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread() + "work");
        countDownLatch.countDown();
    }
}

class WaitTask implements Runnable {

    private CountDownLatch countDownLatch;

    public WaitTask(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println("wait...");
            countDownLatch.await();
            System.out.println("wait end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class CountDownLauntchDemo {

    public static void main(String[] args) {
        final int SIZE = 5;
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new TaskPortion(countDownLatch));
        }
        for (int i = 0; i < 3; i ++) {
            exec.execute(new WaitTask(countDownLatch));
        }
    }


}
