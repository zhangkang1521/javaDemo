package org.zk;

public class Daemon {
	public static void main(String[] args) {
		Thread t = new Thread(new Deamon());
		t.setDaemon(true);
		t.start();
	}

	static class Deamon implements Runnable {

		@Override
		public void run() {
			System.out.println("start");
			SleepUtils.second(2);
			System.out.println("end");
		}
	}
}
