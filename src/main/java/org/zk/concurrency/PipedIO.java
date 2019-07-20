package org.zk.concurrency;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Sender implements Runnable {
    PipedWriter pipedWriter = new PipedWriter();

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                pipedWriter.write('A' + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Receiver implements Runnable {
    private PipedReader pipedReader;

    public Receiver(PipedWriter pipedWriter) {
        try {
            pipedReader = new PipedReader(pipedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("read: " + (char)pipedReader.read());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
public class PipedIO {

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender.pipedWriter);
        exec.execute(sender);
        exec.execute(receiver);
    }
}
