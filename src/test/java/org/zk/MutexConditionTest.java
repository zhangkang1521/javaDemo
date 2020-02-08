package org.zk;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.Assert.*;

public class MutexConditionTest {

	static Lock lock = new Mutex();
	static Condition condition = lock.newCondition();

	public static void main(String[] args) throws Exception {
		lock.lock();
		new Thread(new Task()).start();
		condition.await();
		System.out.println("ok");
		lock.unlock();
	}

	static class Task implements Runnable {
		@Override
		public void run() {
			SleepUtils.second(3);
			System.out.println("task lock");
			lock.lock();
			condition.signal();
			lock.unlock();
		}
	}

}