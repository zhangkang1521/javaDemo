package org.zk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduleThreadPoolTest {

	public static void main(String[] args) {
		ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(2);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
		System.out.println(Thread.currentThread() + " " +sdf.format(new Date()) + " add");
		pool.schedule(new Job(), 2000, TimeUnit.MILLISECONDS);
		pool.schedule(new Job(), 3000, TimeUnit.MILLISECONDS);
	}

	static class Job implements Runnable {
		@Override
		public void run() {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
			System.out.println(Thread.currentThread() + " " +sdf.format(new Date()) + " ===");
		}
	}
}
