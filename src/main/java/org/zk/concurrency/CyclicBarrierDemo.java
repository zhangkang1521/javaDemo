package org.zk.concurrency;

import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangkang on 2019/6/16.
 */

class Worker implements Runnable{

    private CyclicBarrier cyclicBarrier;
    private int id;

    public Worker(int id, CyclicBarrier cyclicBarrier) {
        this.id = id;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public String toString() {
        return "Work #" + id;
    }

    @Override
    public void run() {
        try {
            while(true) {
                System.out.println(this + " work start");
                Thread.sleep(1000 * new Random().nextInt(5));
                System.out.println(this + " work end");
                cyclicBarrier.await(); // 等待所有任务结束
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
public class CyclicBarrierDemo {
    public static void main(String[] args) throws Exception {
        final int SIZE = 2;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(SIZE + 1);
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new Worker(i, cyclicBarrier));
        }
        cyclicBarrier.await();
        System.out.println("all end");
    }
}
