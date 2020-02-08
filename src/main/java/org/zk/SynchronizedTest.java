package org.zk;

public class SynchronizedTest {

	public static void main(String[] args) {
		synchronized (A.class) {
			System.out.println("main ok");

			new Thread(new Job(), "my-thread-1").start();
			SleepUtils.second(1000);
		}


	}

	public static synchronized void f() {

	}

	static class Job implements Runnable {

		@Override
		public void run() {

			synchronized (A.class) {
				System.out.println("ok");
				System.out.println("ok");
				System.out.println("ok");
			}

		}
	}

	static class A {

	}
}
