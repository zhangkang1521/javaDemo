package org.zk;

import java.util.concurrent.CompletableFuture;

/**
 * TODO
 *
 * @author zhangkang
 * @date 2022/5/21 17:49
 */
public class CompletableFutureTest {


    public static void main(String[] args) throws Exception {
//        CompletableFuture.runAsync(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread() + "ok");
//            }
//        });


        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " cf1 do something....");
            return 1;
        });

        CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " cf2 do something....");
            return 2;
        });

        CompletableFuture<Integer> cf3 = cf1.thenCombine(cf2, (a, b) -> {
            System.out.println(Thread.currentThread() + " cf3 do something....");
            return a + b;
        });

        System.out.println("cf3结果->" + cf3.get());

    }

}
