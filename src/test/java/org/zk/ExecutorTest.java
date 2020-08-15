package org.zk;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorTest {

	public static void main(String[] args) throws Exception {
		// core => queue => maxThread => reject
		ExecutorService executor = new ThreadPoolExecutor(2, 3,
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(5));
		final AtomicInteger id = new AtomicInteger();
		for (int i = 0; i < 8; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					int taskId = id.incrementAndGet();
					System.out.println(Thread.currentThread() + " start " + taskId);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread() + " end " + taskId);
				}
			});
			Thread.sleep(1);
		}
	}
}
