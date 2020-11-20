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

	public static void main(String[] args) throws Exception {
		// 内存溢出
//		List<byte[]> list = new ArrayList<>();
//		for (int i = 0; i < 5; i++) {
//			System.out.println(Thread.currentThread() + " set 4MB " + count.incrementAndGet());
//			list.add(new byte[4* 1024 * 1024]);
//			Thread.sleep(1000);
//		}
		// ThreadLocal内存溢出
		ThreadPoolExecutor es = new ThreadPoolExecutor(5, 5,
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		es.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		for (int i = 0; i < 5; i++) {
			es.execute(new Task()); // 使用submit异常信息从Future获取
			Thread.sleep(1000);
		}
		System.out.println("activeCount:" + es.getActiveCount());
		es.shutdown();
	}

	static class Task implements Runnable {
		@Override
		public void run() {
			System.out.println(Thread.currentThread() + " set 4MB " + count.incrementAndGet());
			loginUser.set(new byte[4 * 1024 * 1024]);
			System.out.println(Thread.currentThread() + " set 4MB ok");
		}
	}
}
