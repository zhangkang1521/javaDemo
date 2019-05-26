package org.zk.concurrency;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhangkang on 2019/5/25.
 */
public class AttemptLocking {
    private ReentrantLock lock = new ReentrantLock();

    public void untimed() {
        boolean captured = lock.tryLock();
        try {
            System.out.println(new Date() + " tryLock(): " + captured);
//            Thread.sleep(5000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (captured) {
                lock.unlock();
            }
        }
    }

    public void timed() {
        boolean captured = false;
        try {
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println(new Date() + " tryLock 2 seconds: " + captured);
        } finally {
            if (captured) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        final AttemptLocking al = new AttemptLocking();
        al.untimed();
        al.timed(); // 本线程已拥有锁，返回true
        new Thread(){
//            {
//                setDaemon(true);
//            }
            public void run() {
                al.lock.lock();
                System.out.println(new Date() + " acquired");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                al.lock.unlock();
            }
        }.start();
        Thread.sleep(2);
        al.untimed(); // 未获得锁
        al.timed(); // 超时时间2s内获得锁
    }
}
