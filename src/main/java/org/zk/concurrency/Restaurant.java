package org.zk.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Meal {
    private final int orderNum;

    public Meal(int orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "Meal " + orderNum;
    }
}

class WaitPerson implements Runnable {

    private Restaurant restaurant;

    public WaitPerson(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    while (restaurant.meal == null) {
                        wait();
                    }
                }
                synchronized (restaurant.chef) {
                    Meal meal = restaurant.meal;
                    System.out.println("wait person got " + meal + " start");
                    restaurant.meal = null;
                    Thread.sleep(1000);
                    System.out.println("wait person got " + meal + " end notify chef");
                    restaurant.chef.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Chef implements Runnable {

    private Restaurant restaurant;
    private int count = 0;

    public Chef(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    while (restaurant.meal != null) {
                        wait();
                    }
                }

                synchronized (restaurant.waitPerson) {
                    count++;
                    System.out.println("chef create meal " + count + " start");
                    restaurant.meal = new Meal(count);
                    Thread.sleep(3000);
                    System.out.println("chef create meal " + count + " end, notify waitPerson");
                    restaurant.waitPerson.notifyAll(); // 通知waitPerson取餐
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Restaurant {

    Meal meal;
    ExecutorService exec = Executors.newCachedThreadPool();
    WaitPerson waitPerson = new WaitPerson(this);
    Chef chef = new Chef(this);

    public Restaurant() {
        exec.execute(chef);
        exec.execute(waitPerson);
    }

    public static void main(String[] args) {
        new Restaurant();
    }
}
