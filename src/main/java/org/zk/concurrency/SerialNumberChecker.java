package org.zk.concurrency;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangkang on 2019/5/26.
 */
public class SerialNumberChecker {

    static class SerialChecker implements Runnable {

        Set<Integer> set = new HashSet<Integer>();

        public void run() {
            while(true) {
                int serial = SerialNumberGenerator.nextSerialNumber();
                if (set.contains(serial)) {
                    System.out.println("duplicate: " + serial);
                    System.exit(0);
                }
                set.add(serial);
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 2; i++) {
            exec.execute(new SerialChecker());
        }
    }
}
