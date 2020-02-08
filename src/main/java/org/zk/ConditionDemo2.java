package org.zk;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo2 {

	static Lock lock = new ReentrantLock(true);
	static Condition full = lock.newCondition();

	public static void main(String[] args) throws Exception {
		lock.lock();
		new Thread(new Task(), "task-1").start();
//		SleepUtils.second(20);
		full.await(); // 加入等待队列尾部，释放锁并唤醒同步队列的下一个结点，自己在这里等待
		// 唤醒后尝试重新获取锁
		System.out.println("await end");
	}

	static class Task implements Runnable {



		@Override
		public void run() {
			lock.lock();
			System.out.println(Thread.currentThread() + " lock");
			SleepUtils.second(1);
			System.out.println("signal");
			full.signal();
			SleepUtils.second(5);
			System.out.println("unlock");
			lock.unlock();
		}
	}
}
