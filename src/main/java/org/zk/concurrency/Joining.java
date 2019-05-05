package org.zk.concurrency;

/**
 * Created by zhangkang on 2019/5/3.
 */
public class Joining {
    public static void main(String[] args) throws Exception{
        Sleeper s = new Sleeper("ruimin", 3000);
        Joiner j = new Joiner("kang", s);
        s.interrupt();
    }
}

class Sleeper extends Thread {

    private int duration;

    public Sleeper(String name, int sleepTime) {
        super(name);
        this.duration = sleepTime;
        start();
    }

    public void run() {
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrupted");
            return;
        }
        System.out.println(getName() + " awaken");
    }
}

class Joiner extends Thread {
    private Sleeper sleeper;

    public Joiner(String name, Sleeper sleeper) {
        super(name);
        this.sleeper = sleeper;
        start();
    }

    public void run() {
        System.out.println(getName() + " run");
        try {
            sleeper.join(); // 等待sleeper结束
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
        System.out.println(getName() + " join completed");
    }
}
