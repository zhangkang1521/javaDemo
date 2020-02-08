package org.zk;

public class ReOrder {

	static int x = 0;
	static int y = 0;
	static int a = 0;
	static int b = 0;

	public static void main(String[] args) throws Exception{
		int i = 0;
		while(true) {
			Thread t1 = new Thread(new X());
			Thread t2 = new Thread(new Y());
			t1.start();
			t2.start();
			t1.join();
			t2.join();
			if (x == 0 && y == 0) {
				System.out.println("xxx");
				break;
			}
			System.out.println(i++);
		}
	}

	static class X implements Runnable {
		@Override
		public void run() {
			a = 1;
			x = b;
		}
	}

	static class Y implements Runnable {
		@Override
		public void run() {
			b = 1;
			y = a;
		}
	}
}
