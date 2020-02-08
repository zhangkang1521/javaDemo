package org.zk;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.*;

public class ExecutorsTest {

	public static void main(String[] args) throws Exception{
		ScheduledExecutorService schedule = Executors.newScheduledThreadPool(2);
		System.out.println(new Date());
		ScheduledFuture scheduledFuture = schedule.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println(new Date() + " task 1 start");
//				throw new RuntimeException("xxx"); // 抛出异常后不会再往队列里加入下一次的任务
//				SleepUtils.second(3);
//				System.out.println(new Date() + " task 1 end");
			}
		}, 1, 10, TimeUnit.SECONDS);
		System.out.println("ok");
//		schedule.schedule(new Runnable() {
//			@Override
//			public void run() {
//				System.out.println(new Date() + " task 2 run");
//			}
//		}, 3, TimeUnit.SECONDS);
//		schedule.scheduleAtFixedRate(new Task(1), 0, 2, TimeUnit.SECONDS);
	}


	static class Task implements Runnable {
		private int id;

		public Task(int id) {
			this.id = id;
		}

		@Override
		public void run() {
			System.out.println(new Date() + " " + Thread.currentThread() + " " + id);
			SleepUtils.second(1);
		}
	}
}
