package org.zk.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Horse implements Runnable {

    private static int counter = 0;
    private final int id = counter++;
    private int strides = 0; // 步伐
    private Random random = new Random();
    private static CyclicBarrier cyclicBarrier;

    public Horse(CyclicBarrier barrier) {
        cyclicBarrier = barrier;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                strides += random.nextInt(3);
                cyclicBarrier.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public int getStrides() {
        return strides;
    }

    public String tracks() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < strides; i++) {
            s.append("*");
        }
        return s.toString();
    }

    @Override
    public String toString() {
        return "house " + id;
    }
}

public class HorseRace {

    private CyclicBarrier cyclicBarrier;
    private final int HOUSE_NUM = 3;
    private final int FINISH_LINE = 10;
    private List<Horse> horseList = new ArrayList<>();
    ExecutorService exec = Executors.newCachedThreadPool();

    public HorseRace() {

        cyclicBarrier = new CyclicBarrier(HOUSE_NUM, new Runnable() {
            @Override
            public void run() {
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < FINISH_LINE; i++) {
                    s.append("=");
                }
                System.out.println(s);

                for (Horse horse : horseList) {
                    System.out.println(horse.tracks());
                }
                for (Horse horse : horseList) {
                    if (horse.getStrides() >= FINISH_LINE) {
                        System.out.println(horse + " win!");
                        exec.shutdownNow();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        for (int i=0; i < HOUSE_NUM; i++) {
            Horse horse = new Horse(cyclicBarrier);
            horseList.add(horse);
            exec.execute(horse);
        }
    }

    public static void main(String[] args) {
        new HorseRace();
    }
}
