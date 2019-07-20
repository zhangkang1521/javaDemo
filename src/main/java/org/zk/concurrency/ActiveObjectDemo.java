package org.zk.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by zhangkang on 2019/7/20.
 */
public class ActiveObjectDemo {

//    private ExecutorService exec = Executors.newCachedThreadPool();
    private ExecutorService exec = Executors.newSingleThreadExecutor();
    private Random random = new Random();

    public Future<Integer> calcInt(int x, int y) {

        return exec.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("start calc " + x + ", " + y);
                Thread.sleep(random.nextInt(10000));
                return x + y;
            }
        });
    }

    public static void main(String[] args) throws Exception {
        ActiveObjectDemo ao = new ActiveObjectDemo();
        List<Future> results = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            results.add(ao.calcInt(i, i));
        }
        while (results.size() > 0) {
            for (int i = 0; i < results.size(); i++) {
                Future f = results.get(i);
                if (f.isDone()) {
                    System.out.println(f.get());
                    results.remove(i);
                }
            }
        }
    }
}
