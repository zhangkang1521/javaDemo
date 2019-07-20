package org.zk.concurrency;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

class DelayTask implements Runnable, Delayed{
    private static int count = 0;
    private int id = count++;

    private final int delta;
    private final long trigger;
//    protected static List<DelayTask> sequence = new ArrayList<>();

    public DelayTask(int delaySec) {
        delta = delaySec;
        trigger = System.nanoTime() + NANOSECONDS.convert(delta, SECONDS);
//        sequence.add(this);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        DelayTask that = (DelayTask)o;
        if (trigger < that.trigger) return -1;
        if (trigger > that.trigger) return 1;
        return 0;
    }



    @Override
    public void run() {
        System.out.println(new Date() + " # " + id + ", delta: " + delta);
    }
}

public class DelayQueueDemo {

    public static void main(String[] args) throws Exception {
        Random random = new Random();
        DelayQueue<DelayTask> queue = new DelayQueue<>();
        for (int i = 0; i < 5 ; i++) {
            queue.add(new DelayTask(random.nextInt(10)));
        }
        while (true) {
            queue.take().run();
        }
    }
}
