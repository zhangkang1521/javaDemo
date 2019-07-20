package org.zk.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

class Toast {
    private final int id;
    private static int count = 0;
    public enum Status {DRY, BUTTERED, JAMMED}
    private Status status = Status.DRY;

    public Toast() {
        this.id = count++;
    }

    public void butter() {
        status = Status.BUTTERED;
        System.out.println("butter toast #" + id);
    }

    public void jam() {
        status = Status.JAMMED;
    }

    public String toString() {
        return "#" +id + " " + status;
    }
}

class ButterTask implements Runnable{

    private BlockingQueue<Toast> dryQueue, butteredQueue;

    public ButterTask(BlockingQueue<Toast> dryQueue, BlockingQueue<Toast> butteredQueue) {
        this.dryQueue = dryQueue;
        this.butteredQueue = butteredQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Toast toast = this.dryQueue.take();
                toast.butter();
                this.butteredQueue.put(toast);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class EatTask implements Runnable{

    private BlockingQueue<Toast> butteredQueue;

    public EatTask(BlockingQueue<Toast> butteredQueue) {
        this.butteredQueue = butteredQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Toast toast = this.butteredQueue.take();
                System.out.println("eat toast" + toast);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



public class ToastOMatic {
    public static void main(String[] args) throws Exception{
        BlockingQueue<Toast> dryQueue = new LinkedBlockingDeque<>();
        BlockingQueue<Toast> butterQueue = new LinkedBlockingDeque<>();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new ButterTask(dryQueue, butterQueue));
        exec.execute(new EatTask(butterQueue));
        for (int i = 0; i < 5; i++) {
            dryQueue.put(new Toast());
        }
    }
}
