package org.zk;

import java.util.Date;

public class Join {

    public static void main(String[] args) {
        Thread b = new Thread(new B());
        Thread a = new Thread(new A(b));
        a.start();
        b.start();
    }

    static class A implements Runnable{
        Thread thread;
        public A(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            System.out.println(new Date() + " start");
            try {
                this.thread.join(); // 一直等待，如果thread一直是alive的，thread终止会notify
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(new Date() + " end");
        }
    }

    static class B implements Runnable{

        @Override
        public void run() {
            SleepUtils.second(5);
        }
    }
}
