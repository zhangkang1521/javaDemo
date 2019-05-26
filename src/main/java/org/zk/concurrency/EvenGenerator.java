package org.zk.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by zhangkang on 2019/5/19.
 */
public class EvenGenerator extends IntGenerator {

    private int i = 0;

    private Lock lock = new ReentrantLock();

    public int next() {
        lock.lock();
        try {
            i++;
            Thread.yield();
            i++;
            return i;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        EvenGenerator evenGenerator = new EvenGenerator();
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            executor.execute(new EvenCheck(evenGenerator, i));
        }

        executor.shutdown();
    }
}
