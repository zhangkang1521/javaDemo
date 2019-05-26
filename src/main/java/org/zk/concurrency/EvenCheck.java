package org.zk.concurrency;

/**
 * Created by zhangkang on 2019/5/19.
 */
public class EvenCheck implements Runnable {

    private IntGenerator generator;
    private final int id;

    public EvenCheck(IntGenerator generator, int id) {
        this.generator = generator;
        this.id = id;
    }

    public void run() {
        while (!generator.isCanceled()) {
            int next = generator.next();
//            System.out.println("check: " + next);
            if (next % 2 != 0) {
                System.out.println("id: " + id + ", " + next + " not even!");
                generator.cancel();
            }
        }
    }

}
