package org.zk;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

	static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
		@Override
		public void run() {
			System.out.println("=====");
		}
	});

	public static void main(String[] args) throws Exception {
		new Thread(new Job()).start();
		SleepUtils.second(5);
		cyclicBarrier.await();
		System.out.println("2");


		SleepUtils.second(5);
		new Thread(new Job()).start();
		SleepUtils.second(1);
		cyclicBarrier.await();
		System.out.println("xxx");
	}

	static class Job implements Runnable {
		@Override
		public void run() {
			try {
				cyclicBarrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
			System.out.println("1");
		}
	}

}
