package org.zk.concurrency;


class A {

    public synchronized void f()  {
        System.out.println(Thread.currentThread() + " start wait");
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread() + " wait run");
    }

    public synchronized void g() {
        notifyAll();
    }
}
public class NotifyWaitTest {

    public static void main(String[] args) throws Exception {
        final A a1 = new A();
        final A a2 = new A();

        new Thread() {
            public void run() {
                a1.f();
            }
        }.start();
        new Thread() {
            public void run() {
                a1.f();
            }
        }.start();
        new Thread() {
            public void run() {
                a2.f();
            }
        }.start();

        new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                a1.g(); // 只会唤醒持有a1对象锁wait的线程
            }
        }.start();
    }
}
