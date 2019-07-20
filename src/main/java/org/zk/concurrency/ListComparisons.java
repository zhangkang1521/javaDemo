package org.zk.concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



abstract class Tester<C> {
    ExecutorService exec = Executors.newCachedThreadPool();
    protected int nReaders;
    protected int nWriters;
    // 容器容量
    protected final int CONTAINER_SIZE = 1000;
    protected C container;
    protected CountDownLatch countDownLatch;
    protected long readTime;
    protected long writeTime;

    public Tester(int nReader, int nWriters) {
        this.nReaders = nReader;
        this.nWriters = nWriters;

        this.container = prepareContainer();
        countDownLatch = new CountDownLatch(nReader + nWriters);
    }

    abstract C prepareContainer();

    abstract class TestTask implements Runnable {
        long duration;

        @Override
        public void run() {
            long startTime = System.nanoTime();
            test();
            duration = System.nanoTime() - startTime;
            putResult();
            countDownLatch.countDown();
        }
        // read or write
        abstract void test();

        abstract void putResult();
    }



    public void runTest() {
        startReadAndWrite();
        try {
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(String.format("%6dr %6dw %12d %12d %12d", nReaders, nWriters, readTime, writeTime, readTime + writeTime));
    }

    abstract void startReadAndWrite();

}

abstract class ListTest extends Tester<List<Integer>> {

    public ListTest(int nReader, int nWriters) {
        super(nReader, nWriters);
    }

    class Reader extends TestTask {
        @Override
        public void test() {
            for (int i = 0; i < CONTAINER_SIZE; i++) {
                container.get(i);
            }
        }

        public void putResult() {
            readTime += duration;
        }
    }

    class Writer extends TestTask {

        @Override
        public void test() {
            for (int i = 0; i < CONTAINER_SIZE; i++) {
                container.set(i, i + 1);
            }
        }

        public void putResult() {
            writeTime += duration;
        }
    }

    @Override
    void startReadAndWrite() {
        for (int i = 0; i < nReaders; i++) {
            exec.execute(new Reader());
        }
        for (int i = 0; i < nWriters; i++) {
            exec.execute(new Writer());
        }
    }
}

class SyncListTester extends ListTest {

    public SyncListTester(int nReader, int nWriter) {
        super(nReader, nWriter);
    }

    @Override
    List<Integer> prepareContainer() {
        List<Integer> tmpList = new ArrayList<>(CONTAINER_SIZE);
        for (int i = 0; i < CONTAINER_SIZE; i++) {
            tmpList.add(i);
        }
        return  Collections.synchronizedList(tmpList);
    }
}

class CopyWriteListTester extends ListTest {

    public CopyWriteListTester(int nReader, int nWriter) {
        super(nReader, nWriter);
    }

    @Override
    List<Integer> prepareContainer() {
        List<Integer> tmpList = new ArrayList<>(CONTAINER_SIZE);
        for (int i = 0; i < CONTAINER_SIZE; i++) {
            tmpList.add(i);
        }
        return new CopyOnWriteArrayList(tmpList);
    }
}



public class ListComparisons {
    public static void main(String[] args) {
        System.out.println(String.format("%7s %7s %12s %12s %12s", "Reader", "Writer", "readTime", "writeTime", "readWriteTime"));
        new SyncListTester(10, 0).runTest();
        new SyncListTester(9, 1).runTest();
        new SyncListTester(5, 5).runTest();
        new SyncListTester(1, 9).runTest();
        new CopyWriteListTester(10, 0).runTest();
        new CopyWriteListTester(9, 1).runTest();
        new CopyWriteListTester(5, 5).runTest();
        new CopyWriteListTester(1, 9).runTest();
    }
}
