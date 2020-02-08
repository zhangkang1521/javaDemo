package org.zk;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

	static Lock lock = new ReentrantLock(true);

	public static void main(String[] args) throws Exception {

		lock.lock();
		lock.lock();
		SleepUtils.second(1);
		new Thread(new Task()).start();
		System.in.read();

//		SleepUtils.second(1);
//		System.out.println("unlock 1");
//		lock.unlock();
//		SleepUtils.second(1);
//		System.out.println("unlock 2");
//		lock.unlock();


	}

	static class Task implements Runnable {
		@Override
		public void run() {
			System.out.println(Thread.currentThread() + " lock");
			lock.lock();
			System.out.println(Thread.currentThread() + " locked");
		}
	}
}
