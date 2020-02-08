package org.zk;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolDemo {

	static ExecutorService executor = new ThreadPoolExecutor(1, 2,
			0L, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(1), new ThreadPoolExecutor.CallerRunsPolicy());

	public static void main(String[] args) {
		executor.execute(new Task(1)); // core
		executor.execute(new Task(2)); // queue
		executor.execute(new Task(3)); // maxPoolSize
		executor.execute(new Task(4)); // reject
	}

	static class Task implements Runnable {
		private int id;
		public Task(int id) {
			this.id = id;
		}
		@Override
		public void run() {
//			for(;;) {
				System.out.println(Thread.currentThread() + " run " + id);
				SleepUtils.second(50000);
//			}
		}
	}
}
