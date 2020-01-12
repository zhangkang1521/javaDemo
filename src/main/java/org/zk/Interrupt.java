package org.zk;

public class Interrupt {
	public static void main(String[] args) {
		Thread sleep = new Thread(new Sleep());
		Thread busy = new Thread(new Busy());
		sleep.start();
		busy.start();
		SleepUtils.second(5);
		sleep.interrupt();
		busy.interrupt();
		System.out.println(sleep.isInterrupted());
		System.out.println(busy.isInterrupted());
	}

	static class Sleep implements Runnable {
		@Override
		public void run() {
			while (true) {
				SleepUtils.second(10);
			}
		}
	}

	static class Busy implements Runnable {
		@Override
		public void run() {
			while(true) {

			}
		}
	}
}
