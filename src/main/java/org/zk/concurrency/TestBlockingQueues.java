package org.zk.concurrency;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;

class Producer implements Runnable {
    private final BlockingQueue<Integer> queue;
    int count = 0;

    Producer(BlockingQueue q) {
        queue = q;
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);
                queue.put(produce());
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    Integer produce() {
        System.out.println("produce " + count);
        return count++;
    }
}

class Consumer2 implements Runnable {
    private final BlockingQueue queue;

    Consumer2(BlockingQueue q) {
        queue = q;
    }

    public void run() {
        try {
            while (true) {
                consume(queue.take());
                Thread.sleep(5000);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    void consume(Object x) {
        System.out.println("consume " + x);
    }
}


public class TestBlockingQueues {

    public static void main(String[] args) {
//        BlockingQueue queue = new ArrayBlockingQueue(5);
        BlockingQueue queue = new SynchronousQueue();
        new Thread(new Producer(queue)).start();
        new Thread(new Consumer2(queue)).start();
    }

}
