package org.zk;

public class ConcurrencyTest {

	public static final long count = 1000;

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		concurrency();
		System.out.println(System.currentTimeMillis() - start);
		start = System.currentTimeMillis();
		serial();
		System.out.println(System.currentTimeMillis() - start);
	}

	public static void concurrency() throws Exception {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				int a = 0;
				for (long i = 0; i < count; i++) {
					a+=5;
				}
			}
		});
		thread.start();
		int b = 0;
		for (long i = 0; i < count; i++) {
			b--;
		}
		thread.join();
	}

	public static void serial() throws Exception {
		int a = 0;
		for (long i = 0; i < count; i++) {
			a += 5;
		}
		int b = 0;
		for (long i = 0; i < count; i++) {
			b--;
		}
	}
}
