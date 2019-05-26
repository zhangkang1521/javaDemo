package org.zk.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangkang on 2019/5/26.
 */
public class AtomicityTest implements Runnable {

    private  int i = 0;

    public /*synchronized*/ int getValue() {
        return i;
    }

    private synchronized void evenIncrement() {
        i++;
        i++;
    }

    public void run() {
        while(true) {
            evenIncrement();
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        AtomicityTest a = new AtomicityTest();
        exec.execute(a);
        while(true) {
            int val = a.getValue();
            if (val % 2 != 0) {
                System.out.println(val);
//                System.exit(0);
            }
        }
    }
}
