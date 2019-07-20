package org.zk.concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by zhangkang on 2019/7/20.
 */
public class ReadWriteLockDemo {

    static ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);



    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Reader(lock));
        exec.execute(new Reader(lock));
        Thread.sleep(10);
        exec.execute(new Writer(lock));


    }

    static class Reader implements Runnable{

        ReentrantReadWriteLock lock;

        public Reader(ReentrantReadWriteLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            // 读者之间可以重入，如果有写锁则等待
            Lock  rLock = lock.readLock();
            rLock.lock();
            System.out.println("get read lock");
            try {
                Thread.sleep(new Random().nextInt(10) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rLock.unlock();
                System.out.println("unLock read lock");
            }
        }
    }

    static class Writer implements Runnable{

        ReentrantReadWriteLock lock;

        public Writer(ReentrantReadWriteLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            // 如果有读锁或写锁，都等待
            Lock  wLock = lock.writeLock();
            wLock.lock();
            System.out.println("get write lock");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                wLock.unlock();
                System.out.println("unLock write lock");
            }
        }
    }
}
