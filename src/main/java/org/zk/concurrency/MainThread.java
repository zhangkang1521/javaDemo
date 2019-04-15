package org.zk.concurrency;

/**
 * Created by zhangkang on 2019/4/13.
 */
public class MainThread {

    public static void main(String[] args) {
        LiftOff liftOff = new LiftOff();
        // 并没有启动1个新的线程
        liftOff.run();
    }
}
