package org.zk;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadState {
    static Lock lock = new ReentrantLock();
    public static void main(String[] args) {
//        new Thread(new TimeWaiting(), "timeWaiting").start(); // TimeWaiting
//        new Thread(new Waiting(), "Waiting").start(); // Waiting
//        new Thread(new Block(), "Block-1").start(); // Runnable
//        new Thread(new Block(), "Block-2").start(); // Block
        new Thread(new LockTask(), "lock-task-1").start();
        new Thread(new LockTask(), "lock-task-2").start();
    }

    static class LockTask implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try{
                while(true){

                }
            }finally {
                lock.unlock();
            }
        }
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
