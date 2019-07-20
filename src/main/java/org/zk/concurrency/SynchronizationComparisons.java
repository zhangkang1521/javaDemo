package org.zk.concurrency;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

abstract class Accumulator {
    protected String id;
    protected long value = 0;
    protected static final int PRE_LOAD_SIZE = 10000;
    protected static int[] preLoad;
    protected static Random random = new Random();
    protected volatile int index = 0;
    protected long duration; // 耗时
    private ExecutorService exec = Executors.newCachedThreadPool();

    /** 读写次数 */
    static int cycle = 100;
    private final int READ_WRITE_NUM = 5;
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(2 * READ_WRITE_NUM + 1);

    static {
        preLoad = new int[PRE_LOAD_SIZE];
        for (int i = 0; i < PRE_LOAD_SIZE; i++) {
            preLoad[i] = random.nextInt();
        }
    }


    class Reader implements Runnable {
        @Override
        public void run() {
            for (int i= 0; i < cycle; i++) {
                read();
            }
            try {
                cyclicBarrier.await();
//                System.out.println("read end");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class Writer implements Runnable {
        @Override
        public void run() {
            for (int i= 0; i < cycle; i++) {
                write();
            }
            try {
                cyclicBarrier.await();
//                System.out.println("write end");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    abstract long read();

    abstract void write();

    public void timeTest() {
        long start = System.nanoTime();
        for (int i = 0; i < READ_WRITE_NUM; i++) {
            exec.execute(new SynchronizedTest.Reader());
            exec.execute(new SynchronizedTest.Writer());
        }
        try {
            cyclicBarrier.await();
//            System.out.println("all end");
        } catch (Exception e) {
            e.printStackTrace();
        }
        duration = System.nanoTime() - start;
        System.out.println(String.format("%-12s : %13d", id, duration));
    }

    static void report(Accumulator a1, Accumulator a2) {
        System.out.println(String.format("%-22s : %.2f", a1.id + "/" + a2.id, a1.duration*1.0/a2.duration));
    }

}

class BaseLineTest extends Accumulator {

    { id = "BaseLine"; }

    public long read() {
        return value;
    }

    public void write() {
        value += preLoad[random.nextInt(PRE_LOAD_SIZE)];
    }
}

class SynchronizedTest extends Accumulator {

    { id = "Synchronized"; }

    public synchronized long read() {
        return value;
    }

    public synchronized void write() {
        value += preLoad[random.nextInt(PRE_LOAD_SIZE)];
    }
}

class LockTest extends Accumulator {

    { id = "Lock"; }

    Lock lock = new ReentrantLock();

    public long read() {
        lock.lock();
        try {
            return value;
        } finally {
            lock.unlock();
        }
    }

    public synchronized void write() {
        lock.lock();
        try {
            value += preLoad[random.nextInt(PRE_LOAD_SIZE)];
        } finally {
            lock.unlock();
        }
    }
}

class AtomicTest extends Accumulator {

    { id = "Atomic"; }

    private AtomicLong value = new AtomicLong(0);

    public long read() {
        return value.get();
    }

    public void write() {
        value.getAndAdd(preLoad[random.nextInt(PRE_LOAD_SIZE)]);
    }
}



public class SynchronizationComparisons {

    static BaseLineTest baseLineTest = new BaseLineTest();
    static SynchronizedTest synchronizedTest = new SynchronizedTest();
    static LockTest lockTest = new LockTest();
    static AtomicTest atomicTest = new AtomicTest();


    static void test() {
        System.out.println("==================================");
        System.out.println(String.format("%-12s : %13d", "Cycles", Accumulator.cycle));
        baseLineTest.timeTest();
        synchronizedTest.timeTest();
        lockTest.timeTest();
        atomicTest.timeTest();
        Accumulator.report(synchronizedTest, baseLineTest);
        Accumulator.report(lockTest, baseLineTest);
        Accumulator.report(atomicTest, baseLineTest);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            test();
            Accumulator.cycle *= 2;
        }
    }
}
