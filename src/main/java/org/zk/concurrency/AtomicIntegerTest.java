package org.zk.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhangkang on 2019/5/26.
 */
public class AtomicIntegerTest implements Runnable{

    private AtomicInteger i = new AtomicInteger(0);
    private int i2 = 0;

    public int getValue() {
//        return i.get();
        return i2;
    }

    public void evenIncrement() {
//        i.addAndGet(2);
        i2++;
        i2++;
    }

    public void run() {
        while (true) {
            evenIncrement();
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        AtomicIntegerTest a = new AtomicIntegerTest();
        exec.execute(a);
        while (true) {
            int val = a.getValue();
            if (val % 2 != 0) {
                System.out.println(val);
            }
        }
    }
}
