package org.zk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class FairAndUnFairTest {

	static ReentrantLock2 lock2 = new ReentrantLock2(true);

	public static void main(String[] args) {
		for (int i=0; i < 10; i++)
			new Thread(new Job(), "t"+i).start();
	}

	static class Job implements Runnable {

		@Override
		public void run() {
			lock2.lock();
			lock2.printThreads();
			lock2.unlock();
			lock2.lock();
			lock2.printThreads();
			lock2.unlock();
		}
	}

	static class ReentrantLock2 extends ReentrantLock {

		public ReentrantLock2(boolean fair) {
			super(fair);
		}

		public void printThreads() {
			System.out.print(Thread.currentThread().getName());
			List<Thread> threads = new ArrayList<>(super.getQueuedThreads());
			System.out.print(" wait by ");
			for (Thread thread : threads) {
				System.out.print(thread.getName() + " ");
			}
			System.out.println();
		}
	}
}
