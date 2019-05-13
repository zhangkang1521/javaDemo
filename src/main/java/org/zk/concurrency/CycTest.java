package org.zk.concurrency;

import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by zhangkang on 2019/5/9.
 */
public class CycTest {
    public static void main(String[] args) throws Exception
    {
        ExecutorService executorpool = Executors.newFixedThreadPool(2);

        CycWork work1= new CycWork("a", 1000);
        CycWork work2= new CycWork("b" , 3000);
        CycWork work3= new CycWork("b" , 5000);

        Future<Long> f1 = executorpool.submit(work1);
        Future<Long> f2 = executorpool.submit(work2);
        Future<Long> f3 = executorpool.submit(work3);

        System.out.println(f1.get());
        System.out.println(f2.get());
        System.out.println(f3.get());


        executorpool.shutdown();
        System.out.println("shutdown");

    }

}

class CycWork implements Callable<Long> {


    private String name ;
    private long time;

    public CycWork(String name, long time)
    {
        this .name =name;
        this.time = time;
    }

    public Long call() {
        System. out .println(new Date() + name +" start");

        try {
            Thread.sleep(time);
            System. out .println(new Date() + name +" end" );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }



}
