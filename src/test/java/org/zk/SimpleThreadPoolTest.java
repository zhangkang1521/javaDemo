package org.zk;

import java.util.Date;

import static org.junit.Assert.*;

public class SimpleThreadPoolTest {

	public static void main(String[] args) {
		ThreadPool threadPool = new SimpleThreadPool(2);
		SleepUtils.second(1);
		for (int i = 0; i < 3; i++) {
			threadPool.execute(new TaskA(i));
//			SleepUtils.second(1);
		}
	}

	static class TaskA implements Runnable {

		private int id;

		public TaskA(int id) {
			this.id = id;
		}

		@Override
		public void run() {
			System.out.println(new Date() + " " + Thread.currentThread() + " execute " + id);
			SleepUtils.second(1);
			System.out.println(new Date() + " " + Thread.currentThread() + " execute " + id + " end");
		}
	}

}