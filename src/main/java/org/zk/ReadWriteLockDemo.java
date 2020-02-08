package org.zk;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
	static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
	static Lock readLock = readWriteLock.readLock();
	static Lock writeLock = readWriteLock.writeLock();

	public static void main(String[] args) throws Exception{
		writeLock.lock();
		readLock.lock();


//		writeLock.lock();
		System.out.println("ok");
//		new Thread(new Job()).start();
	}

	static class Job implements Runnable {

		@Override
		public void run() {
			writeLock.lock();
			System.out.println("job read");
		}
	}
}
