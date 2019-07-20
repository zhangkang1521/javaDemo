package org.zk.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

abstract class Incrementable {
    protected long count = 0;
    public abstract void increment();
}

class SyncIncrementable extends Incrementable{

    @Override
    public synchronized void increment() {
        count++;
    }
}

class LockIncrementable extends Incrementable {

    private Lock lock = new ReentrantLock();

    @Override
    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }
}

public class SimpleMicroBenchmark {

    static void test(Incrementable incrementable) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            incrementable.increment();
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void main(String[] args) {
        test(new SyncIncrementable());
        test(new LockIncrementable());
    }
}
