package org.zk.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhangkang on 2019/5/2.
 */
public class SimpleDeamons implements Runnable {
    public void run() {
        try {
            while (true) {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread() + " " + this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new SimpleDeamons());
            t.setDaemon(true); // 设置为后台线程，main方法结束，会杀死所有后台线程，如果设置为普通线程，main进程会等待所有线程结束
            t.start();
        }
        System.out.println("All daemons started");
        TimeUnit.MILLISECONDS.sleep(1000);
    }
}
