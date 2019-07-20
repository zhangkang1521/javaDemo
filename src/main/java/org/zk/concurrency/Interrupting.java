package org.zk.concurrency;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangkang on 2019/6/2.
 */
public class Interrupting {

    private static ExecutorService exec = Executors.newCachedThreadPool();

    static void test(Runnable r) throws InterruptedException{
        Future<?> f = exec.submit(r);
        TimeUnit.SECONDS.sleep(2);
        System.out.println(new Date() + " Interrupting " + r.getClass().getName());
        f.cancel(true); // 中断线程
        System.out.println(new Date() + " Interrupt send to " + r.getClass().getName());
    }

    public static void main(String[] args) throws Exception {
//        test(new SleepBlocked());
//        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());
//        System.exit(0);
    }
}

class SleepBlocked implements Runnable {

    public void run() {
        System.out.println(new Date() + " SleepBlocked run");
        try {
            TimeUnit.SECONDS.sleep(500);
        } catch (InterruptedException e) {
            System.out.println(new Date() + " InterruptedException");
        }
        System.out.println(new Date() + " Exiting SleepBlocked.run()");
    }
}

class IOBlocked implements Runnable {

    private InputStream in;

    public IOBlocked(InputStream in) {
        this.in = in;
    }

    public void run() {
        try {
            System.out.println("waiting for read():");
            in.read();
        } catch (Exception e) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Interrupted from blocked I/O");
            } else {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Exiting IOBlocked run()");
    }
}

class SynchronizedBlocked implements Runnable {

    public synchronized void f() {
        System.out.println(Thread.currentThread().getName() + " run()");
        while(true) {
            Thread.yield();
        }
    }

    public SynchronizedBlocked() {
        new Thread() {
            public void run() {
                f();
            }
        }.start(); // 锁住
    }

    public void run() {
        System.out.println("Trying to call f()");
        f();
        System.out.println("Exiting Synchronized.run()");
    }
}
