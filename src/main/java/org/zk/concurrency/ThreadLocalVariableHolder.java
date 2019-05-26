package org.zk.concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Accessor implements Runnable {

    private final int id;

    public Accessor(int id) {
        this.id = id;
    }

    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
//        while(true) {
            ThreadLocalVariableHolder.increment();
            System.out.println(this);
            Thread.yield();
        }
    }

    public String toString() {
        return "# " + id + ": " + ThreadLocalVariableHolder.get();
    }
}

public class ThreadLocalVariableHolder {

    private static ThreadLocal<Integer> value = new ThreadLocal<Integer>() {

        private Random random = new Random();

        @Override
        protected Integer initialValue() {
            return random.nextInt(1000);
        }
    };

    public static void increment() {
        value.set(value.get() + 1);
    }

    public static int get() {
        return value.get();
    }

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Accessor(i));
        }
        Thread.sleep(2000);
        exec.shutdownNow();

    }
}
