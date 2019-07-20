package org.zk.concurrency;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

abstract class MapTester extends Tester<Map<Integer, Integer>> {

    public MapTester(int nReader, int nWriters) {
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
                container.put(i, i + 1);
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

class SyncMapTester extends MapTester {

    public SyncMapTester(int nReader, int nWriter) {
       super(nReader, nWriter);
    }

    @Override
    Map<Integer, Integer> prepareContainer() {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < CONTAINER_SIZE; i++) {
            map.put(i, i);
        }
        return Collections.synchronizedMap(map);
    }
}

class ConCurrentMapTester extends MapTester {

    public ConCurrentMapTester(int nReader, int nWriter) {
        super(nReader, nWriter);
    }

    @Override
    Map<Integer, Integer> prepareContainer() {
        Map<Integer, Integer> map = new ConcurrentHashMap<>();
        for (int i = 0; i < CONTAINER_SIZE; i++) {
            map.put(i, i);
        }
        return map;
    }
}

public class MapComparisons {
    public static void main(String[] args) {
        // concurrentHashMap性能较好
        System.out.println(String.format("%7s %7s %12s %12s %12s", "Reader", "Writer", "readTime", "writeTime", "readWriteTime"));
        new SyncMapTester(10, 0).runTest();
        new SyncMapTester(9, 1).runTest();
        new SyncMapTester(5, 5).runTest();
        new SyncMapTester(1, 9).runTest();
        new ConCurrentMapTester(10, 0).runTest();
        new ConCurrentMapTester(9, 1).runTest();
        new ConCurrentMapTester(5, 5).runTest();
        new ConCurrentMapTester(1, 9).runTest();
    }
}
