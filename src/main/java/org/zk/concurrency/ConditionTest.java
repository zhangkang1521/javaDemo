package org.zk.concurrency;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest implements Runnable{

    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    private boolean signal = false;

    public ConditionTest(boolean signal) {
        this.signal = signal;
    }

    public void f1() {
        lock.lock();
        try {
            System.out.println("f1 signal");
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void f2() {
        lock.lock();
        try {
            System.out.println("f2 wait");
            condition.await();
            System.out.println("f2 wait end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void run() {
        if (signal) {
            f1();
        }else {
            f2();
        }
    }

    public static void main(String[] args) throws Exception{
        new Thread(new ConditionTest(false)).start();
        Thread.sleep(3000);
        new Thread(new ConditionTest(true)).start();
    }
}
