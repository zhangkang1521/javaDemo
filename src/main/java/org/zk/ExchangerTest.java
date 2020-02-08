package org.zk;

import java.util.concurrent.Exchanger;

public class ExchangerTest {

	static Exchanger<String> exchanger = new Exchanger<>();

	public static void main(String[] args) throws Exception {
		new Thread(new Task()).start();
		String b = exchanger.exchange("111");
		System.out.println("main get from thread 0:" + b);
	}

	static class Task implements Runnable {
		@Override
		public void run() {
			SleepUtils.second(10);
			try {
				String b = "222";
				String a = exchanger.exchange(b);
				if (b.equals(a)) {
					System.out.println("ok");
				} else {
					System.out.println("diff a:" + a + ", but b:" + b);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
