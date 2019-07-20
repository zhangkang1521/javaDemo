package org.zk.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

class Chopstick {
    private static int count = 0;
    private  final int id = count++;
    private boolean taken = false;
    public synchronized void take() {
        try {
            while (taken) {
                wait();
            }
            taken = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void drop() {
        taken = false;
        notifyAll();
    }

    @Override
    public String toString() {
        return "chopstick #" + id;
    }
}
public class Philosopher implements Runnable {
    private  final int id;
    private Chopstick left;
    private Chopstick right;

    public Philosopher(int id, Chopstick left, Chopstick right) {
        this.id = id;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return Thread.currentThread() +" Philosopher #" + id;
    }

    public void run() {
        try {
            while (true) {
                left.take();
                System.out.println(this + " take " + left);
                right.take();
                System.out.println(this + " take " + right);
                right.drop();
                System.out.println(this + " drop " + right);
                left.drop();
                System.out.println(this + " drop " + left);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Chopstick left = new Chopstick();
        Chopstick right = new Chopstick();
        Philosopher p1 = new Philosopher(0, left, right);
        Philosopher p2 = new Philosopher(1, right, right); // 死锁
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(p1);
        exec.execute(p2);
//        exec.shutdownNow();
    }

}
