package org.zk;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * -Xms20m -Xmx20m -Xmn10m
 */
public class ThreadLocalTest {

	static ThreadLocal<byte[]> loginUser = new ThreadLocal();
	private static AtomicInteger count = new AtomicInteger(0);

	public static void main(String[] args) throws Exception{
		System.in.read();
		// 内存溢出
//		List<byte[]> list = new ArrayList<>();
//		for (int i = 0; i < 25; i++) {
//			System.out.println(Thread.currentThread() + " set 1MB " + count.incrementAndGet());
//			list.add(new byte[1024 * 1024]);
//			Thread.sleep(1000);
//		}
		// ThreadLocal内存溢出
		ThreadPoolExecutor es = new ThreadPoolExecutor(20, 20,
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		for (int i = 0; i < 20; i++) {
			es.submit(new Task());
			System.out.println("activeCount:" + es.getActiveCount());
			Thread.sleep(1000);
		}

	}

	static class Task implements Runnable {
		@Override
		public void run() {
			System.out.println(Thread.currentThread() + " set 1MB " + count.incrementAndGet());
			loginUser.set(new byte[1024*1024]);
			//			loginUser.remove();
			try {
				Thread.sleep(21000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread() + " get " + loginUser.get());

		}
	}
}
