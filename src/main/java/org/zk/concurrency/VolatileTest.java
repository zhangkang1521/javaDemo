package org.zk.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangkang on 2019/5/18.
 */
public class VolatileTest {

    volatile int a = 0;

    public  void increase() {
        for (int i = 0; i < 10000; i++) {
            a++; // 读取变量的原始值、进行加1操作、写入工作内存 volatile保证了可见性，但并没有保证原子性
        }
    }


    public static void main(String[] args) throws Exception {

        final VolatileTest volatileTest = new VolatileTest();
        for (int i = 0; i < 10; i++) {
            new Thread() {
                public void run() {
                    volatileTest.increase();
                }
            }.start();
        }
        Thread.sleep(1000);
        System.out.println(volatileTest.a);
    }
}

