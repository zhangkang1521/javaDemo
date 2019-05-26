package org.zk;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(new A());
        executor.execute(new B());
        executor.shutdown();
    }
}

class A implements Runnable{

    public void run() {
        while(true) {
            System.out.println("a");
        }
    }
}

class B implements Runnable{

    public void run() {

    }
}
