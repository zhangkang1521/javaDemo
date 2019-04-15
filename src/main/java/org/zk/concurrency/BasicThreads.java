package org.zk.concurrency;

/**
 * Created by zhangkang on 2019/4/13.
 */
public class BasicThreads {
    public static void main(String[] args) {
        Thread t = new Thread(new LiftOff());
        t.start();
        System.out.println("Waitting for LiftOff");
    }
}
