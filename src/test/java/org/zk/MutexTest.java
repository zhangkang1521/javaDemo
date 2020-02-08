package org.zk;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import static org.junit.Assert.*;

public class MutexTest {

	static Mutex lock = new Mutex();

	public static void main(String[] args) throws Exception {
		lock.lock();
		Thread thread = new Thread(new Task(), "task-1");
		Thread thread2 = new Thread(new Task(), "task-2");
		Thread thread3 = new Thread(new Task(), "task-3");
		thread.start();
		SleepUtils.second(1);
		thread2.start();
		SleepUtils.second(1);
		thread3.start();
		SleepUtils.second(1);
		System.out.println(lock.getQueuedThreads());
	}

	static class Task implements Runnable {
		@Override
		public void run() {
			System.out.println(Thread.currentThread() + " lock 1");
			lock.lock();
			System.out.println(Thread.currentThread() + " lock 1 success");

		}
	}

}