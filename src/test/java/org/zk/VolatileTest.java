package org.zk;

import org.junit.Test;

/**
 * @Author: zhangkang
 * @CreateTime: 2025-04-16 16:50
 */
public class VolatileTest {

    private static volatile boolean flag = false;

    private static int a = 0;

    private static int count = 0;

    @Test
    public void test() {
        new Thread(new Task()).start();
        new Thread(new Task()).start();

        while (true) {
            if (flag && a == 0) { // 这个不是原子的
                System.out.println("指令重排序错误 " + count);
                break;
            }
        }
    }


    static class Task implements Runnable {

        @Override
        public void run() {
            while (true) {
                if (flag) {
                    a = 1;
                } else {
                    a = 0;
                }
                flag = !flag;
                count++;
            }
        }
    }
}
