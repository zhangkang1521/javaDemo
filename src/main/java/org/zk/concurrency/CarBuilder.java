package org.zk.concurrency;

import java.util.concurrent.*;

class Car2 {
    private static int count = 0;
    private final int id = count++;
    private boolean wheel = false;
    private boolean engine = false;

    public void addWheel() {
        wheel = true;
    }

    public void addEngine() {
        engine = true;
    }

    @Override
    public String toString() {
        return "car" + id;
    }
}

/**
 * 底盘构造
 */
class ChassisBuilder implements Runnable {

    BlockingQueue<Car2> carQueue;

    public ChassisBuilder(BlockingQueue<Car2> carQueue) {
        this.carQueue = carQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Car2 car = new Car2();
                System.out.println("生产底盘" + car);
                this.carQueue.add(car);
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Assembler implements Runnable {

    BlockingQueue<Car2> carQueue;
    Car2 car;
    CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
    EngineRobot engineRobot;
    WheelRobot wheelRobot;

    public Assembler(BlockingQueue<Car2> carQueue, EngineRobot engineRobot, WheelRobot wheelRobot) {
        this.carQueue = carQueue;
        this.engineRobot = engineRobot;
        this.wheelRobot = wheelRobot;
    }


    @Override
    public void run() {
        try {
            while (true) {
                car = carQueue.take();
                engineRobot.engage(car, cyclicBarrier);
                wheelRobot.engage(car, cyclicBarrier);
                cyclicBarrier.await();
                System.out.println(car + "组装完成");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

abstract class AbstractRobot implements Runnable {
    Car2 car;
    CyclicBarrier cyclicBarrier;
    boolean engage = false;


    public synchronized void engage(Car2 car, CyclicBarrier barrier) {
        this.car = car;
        this.cyclicBarrier = barrier;
        engage = true;
        notifyAll();
    }


    @Override
    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    while (!engage) {
                        wait();
                    }
                }
                service();
                this.cyclicBarrier.await();
                engage = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    abstract void service();


}

class EngineRobot extends AbstractRobot {

    @Override
    void service() {
        try {
            Thread.sleep(3000);
            this.car.addEngine();
            System.out.println(this.car + "加上引擎");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class WheelRobot extends AbstractRobot {

    @Override
    void service() {
        try {
            Thread.sleep(1000);
            this.car.addWheel();
            System.out.println(this.car + "加上轮胎");
        } catch (InterruptedException e) {

        }
    }
}


public class CarBuilder {

    public static void main(String[] args) {
        BlockingQueue<Car2> carQueue = new LinkedBlockingQueue<>();
        ExecutorService exec = Executors.newCachedThreadPool();
        ChassisBuilder chassisBuilder = new ChassisBuilder(carQueue);
        EngineRobot engineRobot = new EngineRobot();
        WheelRobot wheelRobot = new WheelRobot();
        Assembler assembler = new Assembler(carQueue, engineRobot, wheelRobot);
        exec.execute(chassisBuilder);
        exec.execute(engineRobot);
        exec.execute(wheelRobot);
        exec.execute(assembler);
    }
}
