package org.zk;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

	static CountDownLatch countDownLatch = new CountDownLatch(2);

	public static void main(String[] args) throws Exception {
		new Thread(new Job()).start();
		new Thread(new Job2()).start();
		countDownLatch.await();
		System.out.println("ok");
	}

	static class Job implements Runnable {
		@Override
		public void run() {
			SleepUtils.second(2);
			countDownLatch.countDown();
			System.out.println("111");
		}
	}

	static class Job2 implements Runnable {
		@Override
		public void run() {
			SleepUtils.second(2);
			countDownLatch.countDown();
			System.out.println("222");
		}
	}
}
