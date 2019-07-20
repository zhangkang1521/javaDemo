package org.zk.concurrency;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangkang on 2019/6/2.
 */
public class CloseResource {
    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        ServerSocket server = new ServerSocket(8080);
        InputStream socketInput = new Socket("localhost", 8080).getInputStream();

//        exec.execute(new IOBlocked(socketInput));
        exec.execute(new IOBlocked(System.in));

        Thread.sleep(1000);
        System.out.println("shut down all thread");
        exec.shutdownNow();

//        Thread.sleep(1000);
//        System.out.println("closing " + socketInput.getClass().getName());
//        socketInput.close();

        Thread.sleep(1000);
        System.out.println("closing " + System.in.getClass().getName());
        System.in.close();
    }
}
