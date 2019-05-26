package org.zk.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

class Pair {
    private int x, y;

    public Pair() {
        this.x = 0;
        this.y = 0;
    }

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void incrementX() {
        x++;
    }

    public void incrementY() {
        y++;
    }

    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }


    public class PairValuesNotEqualException extends RuntimeException {
        public PairValuesNotEqualException() {
            super("Pair values not equal: " + Pair.this);
        }
    }

    public void checkState() {
        if (x != y) {
            throw new PairValuesNotEqualException();
        }
    }
}

abstract class PairManager {

    protected Pair pair = new Pair();
    AtomicInteger checkCounter = new AtomicInteger();


    // 模板方法
    public abstract void increment();

    public synchronized Pair getPair() {
        return new Pair(pair.getX(), pair.getY());
    }
}

class PairManager1 extends PairManager {

    public synchronized void increment() {
        pair.incrementX();
        pair.incrementY();
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class PairManager2 extends PairManager {

    public void increment() {
        synchronized (this) {
            pair.incrementX();
            pair.incrementY();
        }
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class PairManipulator implements Runnable {

    private PairManager pm;

    public PairManipulator(PairManager pm) {
        this.pm = pm;
    }

    public void run() {
        while (true) {
            pm.increment();
        }
    }
}

class PairChecker implements Runnable {

    private PairManager pm;

    public PairChecker(PairManager pm) {
        this.pm = pm;
    }

    public void run() {
        while(true) {
            pm.checkCounter.incrementAndGet();
            pm.getPair().checkState();
        }
    }
}

public class CriticalSection {

    public static void main(String[] args) throws Exception {
        PairManager pm1 = new PairManager1();
        PairManager pm2 = new PairManager2();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new PairManipulator(pm1));
        exec.execute(new PairManipulator(pm2));
        exec.execute(new PairChecker(pm1));
        exec.execute(new PairChecker(pm2));
        Thread.sleep(5000);
        System.out.println(pm1.checkCounter);
        System.out.println(pm2.checkCounter);
        exec.shutdown();
        System.exit(0);
    }
}
