package org.zk.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhangkang on 2019/6/9.
 */
public class Interrupting2 {
    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new Blocked2());
        t.start();
        TimeUnit.SECONDS.sleep(5);
        System.out.println("t.interrupt()");
        t.interrupt();
    }
}

class BlockedMutex {

    private Lock lock = new ReentrantLock();

    public BlockedMutex() {
        lock.lock();
    }

    public void f() {
        try {
            System.out.println("in f()");
            lock.lockInterruptibly(); // 可以被中断的锁
        } catch (InterruptedException e) {
            System.out.println("Interrupted in f()");
        }
    }
}

class Blocked2 implements Runnable {

    BlockedMutex blockedMutex = new BlockedMutex();

    public void run() {
        System.out.println("waiting for f()");
        blockedMutex.f();
        System.out.println("run end");
    }
}
