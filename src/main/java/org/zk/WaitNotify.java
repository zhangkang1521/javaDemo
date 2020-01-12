package org.zk;

import java.util.Date;

public class WaitNotify {

	static Object o = new Object();

	public static void main(String[] args) {
		Wait wait = new Wait();
		new Thread(wait).start();
		SleepUtils.second(1);
		Notify notify = new Notify();
		new Thread(notify).start();

	}

	static class Wait implements Runnable {
		@Override
		public void run() {
			synchronized (o) {
				System.out.println(new Date() + " wait...");
				try {
					o.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(new Date() +" wait end");
			}
		}
	}

	static class Notify implements Runnable {

		@Override
		public void run() {
			synchronized (o) {
				SleepUtils.second(1);
				o.notify();
				System.out.println(new Date() + " notify");
				SleepUtils.second(1);
			}
		}
	}
}
