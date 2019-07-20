package org.zk.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangkang on 2019/6/2.
 */
public class OrnamentalGarden {

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Entrance(i));
        }
        Thread.sleep(5);
        Entrance.cancel();
        exec.shutdown();
        // 等待，如果所有线程全部结束返回true
        if (!exec.awaitTermination(100, TimeUnit.MILLISECONDS)) {
            System.out.println("Some tasks were not terminated");
        }
        System.out.println("totalCount: " + Entrance.getTotalCount());
        System.out.println("sumEntrance: " + Entrance.sumEntrances());
    }
}

class Count {
    private int count = 0;

    public synchronized int increment() {
        int temp = count;
        Thread.yield();
        return (count = ++temp);
    }

    public synchronized int value() {
        return count;
    }
}

class Entrance implements Runnable {

    private static Count count = new Count();
    private static List<Entrance> entrances = new ArrayList<Entrance>();
    private int number = 0;
    private final int id;
    private static volatile boolean canceled = false;

    public static void cancel() {
        canceled = true;
    }

    public Entrance(int id) {
        this.id = id;
        entrances.add(this);
    }

    public void run() {
        while(!canceled) {
            ++number;
            System.out.println("Entrance " + id + ": " + number + ", Total: " + count.increment());
        }
    }

    public int getValue() {
        return number;
    }

    public static int getTotalCount() {
        return count.value();
    }

    public static int sumEntrances() {
        int sum = 0;
        for (Entrance e : entrances) {
            sum += e.number;
        }
        return sum;
    }
}
