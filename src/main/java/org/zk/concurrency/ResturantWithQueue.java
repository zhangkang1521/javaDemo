package org.zk.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;


class Consumer3 implements Runnable {
    private static int count = 0;
    private int id = count++;
    private Waiter waiter; // 一个客户有单独的服务员为他服务
    SynchronousQueue<Plate> synchronousQueue = new SynchronousQueue<>();

    public Consumer3(Waiter waiter) {
        this.waiter = waiter;
    }

    /**
     * 接盘子
     * @param plate
     */
    public void deliver(Plate plate) {
        try {
           synchronousQueue.put(plate);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            //while (!Thread.interrupted()) {
                waiter.placeOrder(this, "food" + new Random().nextInt(100));
                Plate plate = synchronousQueue.take();
                System.out.println(this + "接到盘子，开吃 " + plate.food);
                Thread.sleep(2000);
            // }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "顾客" + id;
    }
}

class Order {
    Consumer3 consumer3;
    Waiter waiter;
    String food;

    public Order(Consumer3 consumer3, Waiter waiter, String food) {
        this.consumer3 = consumer3;
        this.waiter = waiter;
        this.food = food;
    }

    @Override
    public String toString() {
        return "[顾客" + consumer3 + "，食物" + food + "，服务员：" + waiter + "]";
    }
}


class Waiter implements Runnable {
    private static int count = 0;
    private int id = count++;
    Resturant3 resturant;
    BlockingQueue<Plate> plates = new LinkedBlockingDeque<>();


    public Waiter(Resturant3 resturant) {
        this.resturant = resturant;
    }

    /**
     * 接单
     * @param consumer3
     * @param food
     */
    public void placeOrder(Consumer3 consumer3, String food) {
        try {
            Order order = new Order(consumer3, this, food);
            System.out.println(this + " 接到订单 " + order);
            resturant.orders.put(order);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Plate plate = plates.take();
                System.out.println(this + "将盘子递给" + plate.order.consumer3);
                plate.order.consumer3.deliver(plate);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "服务员" + id;
    }
}

class Plate {
    Order order;
    String food;

    public Plate(Order order, String food) {
        this.order = order;
        this.food = food;
    }

    @Override
    public String toString() {
        return "盘子【食物：" + food + "，顾客" + order.consumer3 + "】";
    }
}

class Chef3 implements Runnable {
    private static int count = 0;
    private int id = count++;
    Resturant3 resturant;

    public Chef3(Resturant3 resturant) {
        this.resturant = resturant;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Order order = resturant.orders.take();
                System.out.println(this + "拿到订单" + order + "，开始做饭");
                Thread.sleep(1000);
                Plate plate = new Plate(order, order.food);
                System.out.println(this + "将" + plate + "给服务员" + order.waiter);
                order.waiter.plates.put(plate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "厨师" + id;
    }
}



class Resturant3 implements Runnable {
    BlockingQueue<Order> orders = new LinkedBlockingDeque<>();
    List<Waiter> waiters = new ArrayList<>();
    List<Chef3> chefs = new ArrayList<>();
    ExecutorService exec;
    private Random random = new Random();
    private final int WAITER_SIZE = 5;
    private final int CHEF_SIZE = 2;

    public Resturant3(ExecutorService exec) {
        this.exec = exec;
    }



    @Override
    public void run() {
        try {
            for (int i = 0; i < WAITER_SIZE; i++) {
                Waiter waiter = new Waiter(this);
                exec.execute(waiter);
                waiters.add(waiter);
            }
            for (int i = 0; i < CHEF_SIZE; i++) {
                Chef3 chef3 = new Chef3(this);
                exec.execute(chef3);
                chefs.add(chef3);
            }


            while (!Thread.interrupted()) {
                // 模拟不断有顾客进来
                Consumer3 consumer = new Consumer3(waiters.get(random.nextInt(WAITER_SIZE)));
                exec.execute(consumer);
                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
public class ResturantWithQueue {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        Resturant3 resturant3 = new Resturant3(exec);
        exec.execute(resturant3);
    }
}
