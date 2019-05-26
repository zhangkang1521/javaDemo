package org.zk.concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangkang on 2019/5/26.
 */
public class ArrayListTest implements Runnable {

    final List<Integer> list = new ArrayList<Integer>();
//    final List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());

    public void run() {
        for (int i = 0; i < 1000; i++) {
            list.add(i);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ArrayListTest arrayListTest = new ArrayListTest();
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 2; i++) {
            exec.execute(arrayListTest);
        }

        Thread.sleep(2000);
        for (int i = 0; i < arrayListTest.list.size(); i++) {
            if (arrayListTest.list.get(i) == null) {
                System.out.println(i + ": " + arrayListTest.list.get(i));
            }
        }
        exec.shutdown();
    }
}
