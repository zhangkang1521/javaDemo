package org.zk;

public class ThreadState {
    public static void main(String[] args) {
        new Thread(new TimeWaiting(), "timeWaiting").start(); // TimeWaiting
        new Thread(new Waiting(), "Waiting").start(); // Waiting
        new Thread(new Block(), "Block-1").start(); // Runnable
        new Thread(new Block(), "Block-2").start(); // Block
    }

    static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while(true) {
                SleepUtils.second(100);
            }
        }
    }

    static class Waiting implements Runnable {
        @Override
        public void run() {
            while(true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class Block implements Runnable {
        @Override
        public void run() {
            synchronized (Block.class) {
                while(true) {

                }
            }
        }
    }
}
