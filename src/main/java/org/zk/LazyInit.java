package org.zk;

public class LazyInit {
	private static volatile LazyInit instance;

	public static  LazyInit getInstance() {
		if (instance == null) {
			System.out.println("XXX");
			instance = new LazyInit();
		}
		return instance;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			new Thread(new Task()).start();
		}
	}

	static class Task implements Runnable {
		@Override
		public void run() {
			LazyInit.getInstance();
		}
	}
}
