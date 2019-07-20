package org.zk.concurrency;

/**
 * Created by zhangkang on 2019/6/9.
 */
public class MultiLock {

    public synchronized void f1(int count) {
        if (count-- > 0) {
            System.out.println("f1");
            f2(count);
        }
    }

    public synchronized void f2(int count) {
        if (count-- > 0) {
            System.out.println("f2");
            f1(count);
        }
    }

    public static void main(String[] args) {
        // 同一个互斥可以被同一个任务多次获得
        final MultiLock multiLock = new MultiLock();
        new Thread() {
            public void run() {
                multiLock.f1(5);
            }
        }.start();
    }
}
