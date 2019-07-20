package org.zk.concurrency;

import java.util.Date;
import java.util.concurrent.TimeUnit;

class Car {
    private boolean waxOn = false;

    public synchronized void waxed() {
        waxOn = true;
        notifyAll();
    }

    public synchronized void buffed() {
        waxOn = false;
        notifyAll();
    }

    public synchronized void waitFoxWaxing() throws InterruptedException {
        while (waxOn == false) {
//            System.out.println("waitFoxWaxing");
            wait();
        }
    }

    public synchronized void waitForBuffing() throws InterruptedException {
        while(waxOn == true) {
//            System.out.println("waitForBuffing");
            wait();
        }
    }
}

class WaxOn implements Runnable {

    private Car car;

    public WaxOn(Car car) {
        this.car = car;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println(new Date() + " wax on start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(new Date() + " wax on end");
                car.waxed();
                car.waitForBuffing();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class WaxOff implements Runnable {

    private Car car;

    public WaxOff(Car car) {
        this.car = car;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                car.waitFoxWaxing();
                System.out.println(new Date() + " wax off start");
                TimeUnit.SECONDS.sleep(1);
                car.buffed();
                System.out.println(new Date() + " wax off end");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class WaxOMatic {

    public static void main(String[] args) throws Exception{
        Car car = new Car();
        new Thread(new WaxOn(car)).start();
        new Thread(new WaxOff(car)).start();
    }
}
