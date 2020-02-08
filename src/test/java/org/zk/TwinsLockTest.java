package org.zk;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class TwinsLockTest {

	static TwinsLock twinsLock = new TwinsLock();

	public static void main(String[] args) {
		twinsLock.lock();
		twinsLock.lock();


		Thread thread = new Thread(new TwinsLockTest.Task(), "task-1");
		thread.start();
		SleepUtils.second(1);

		System.out.println("unlock");
		twinsLock.unlock();
	}

	static class Task implements Runnable {
		@Override
		public void run() {
			System.out.println(Thread.currentThread() + " lock 1");
			twinsLock.lock();
			System.out.println(Thread.currentThread() + " lock 1 success");
		}
	}

}