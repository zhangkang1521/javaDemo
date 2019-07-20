package org.zk.concurrency;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Consumer {
    private static int count = 0;
    private final int id = count++;
    private static Random random = new Random();
    private int serveTime = random.nextInt(10);

    @Override
    public String toString() {
        return " " + serveTime;
    }

    public int getServeTime() {
        return serveTime;
    }
}

class Teller implements Runnable {
    private static int count = 0;
    private int id = count++;
    BlockingQueue<Consumer> consumers;
    private boolean doOtherThing = false;

    public Teller(BlockingQueue<Consumer> consumers) {
        this.consumers = consumers;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Consumer consumer = consumers.take();
                System.out.println(this + " service " + consumer);
                Thread.sleep(consumer.getServeTime() * 1000);
                while (doOtherThing) {
                    synchronized (this) {
                        System.out.println(this + " wait");
                        wait();
                        System.out.println(this + " end wait");
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doOtherThing() {
        this.doOtherThing = true;
    }

    public synchronized void work() {
        this.doOtherThing = false;
        notifyAll();
    }

    @Override
    public String toString() {
        return "Teller #" + id;
    }
}

class ConsumerProducer implements Runnable {

    BlockingQueue<Consumer> consumers;

    public ConsumerProducer(BlockingQueue<Consumer> consumers) {
        this.consumers = consumers;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Consumer consumer = new Consumer();
                this.consumers.put(consumer);
//                System.out.println("生产：" + consumer);
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class TellerManager implements Runnable{

    private BlockingQueue<Consumer> consumers;
    private Queue<Teller> tellersWork = new LinkedList<>();
    private Queue<Teller> tellersDoOtherThing = new LinkedList<>();
    private ExecutorService exec;

    public TellerManager(ExecutorService exec, BlockingQueue<Consumer> consumers) {
        this.exec = exec;
        this.consumers = consumers;
    }

    public void adjustTeller() {
        System.out.println("consumersSize: " + consumers.size() + "，TellerSize: " + tellersWork.size());
        if (consumers.size() / tellersWork.size() >= 2) {
            if (tellersDoOtherThing.size() > 0) {
                Teller teller = tellersDoOtherThing.poll();
                teller.work();
                tellersWork.offer(teller);
                System.out.println("teller 资源不足，添加正在做其他事情的teller " + teller);
            } else {
                Teller teller = new Teller(consumers);
                exec.execute(teller);
                tellersWork.offer(teller);
                System.out.println("teller 资源不足，添加新teller " + teller);
            }
            return;
        }
        if (tellersWork.size() > 1 && consumers.size() / tellersWork.size() < 2) {
            Teller teller1 = tellersWork.poll();
            teller1.doOtherThing();
            tellersDoOtherThing.offer(teller1);
            System.out.println("teller 资源过多，移除 " + teller1);
        }
    }


    @Override
    public void run() {
        try {
            // 先添加一个Teller
            Teller teller = new Teller(consumers);
            exec.execute(teller);
            tellersWork.add(teller);

            while(!Thread.interrupted()) {
                Thread.sleep(5000);
                adjustTeller();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class BankTellerSimulation {

    public static void main(String[] args) {
        BlockingQueue consumers = new ArrayBlockingQueue(10);
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new ConsumerProducer(consumers));
        exec.execute(new TellerManager(exec, consumers));

    }
}
