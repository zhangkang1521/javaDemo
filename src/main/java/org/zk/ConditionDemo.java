package org.zk;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {
	static AtomicInteger atomicInteger = new AtomicInteger(0);
	static LinkedList<Integer> linkedList = new LinkedList();
	public static final int SIZE = 10;
	static Lock lock = new ReentrantLock();
	static Condition full = lock.newCondition();
	static Condition empty = lock.newCondition();

	public static void main(String[] args) {
		new Thread(new Producer(), "producer-1").start();
		new Thread(new Consumer(), "consumer-1").start();
		new Thread(new Consumer(), "consumer-2").start();
	}

	static class Producer implements Runnable {
		@Override
		public void run() {
			while(true) {
				lock.lock();
				try {
					if (linkedList.size() >= SIZE) {
						System.out.println(Thread.currentThread() +" producer await...");
						full.await();
					}
					int i = atomicInteger.incrementAndGet();
					System.out.println(Thread.currentThread() +" produce " + i);
					linkedList.add(i);
					empty.signal();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
				SleepUtils.second(1);
			}
		}
	}

	static class Consumer implements Runnable {
		@Override
		public void run() {
			while(true) {
				lock.lock();
				try {
					while (linkedList.size() <= 0) {
						System.out.println(Thread.currentThread() +" consumer await...");
						empty.await(); // 释放锁，进入等待队列，从同步队列的头结点移到等待队列的尾部
					}
					Integer i = linkedList.removeLast();
					System.out.println(Thread.currentThread() + " consume " + i);
					full.signal();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
				SleepUtils.second(1);
			}

		}
	}
}
