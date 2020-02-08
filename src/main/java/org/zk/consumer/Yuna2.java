package org.zk.consumer;

import com.sun.org.apache.xml.internal.dtm.ref.EmptyIterator;
import org.zk.SleepUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Yuna2 {

	static BlockingQueue<String> queue = new LinkedBlockingQueue(1000);
	static ExecutorService executorService = new ThreadPoolExecutor(16, 16, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(1000));

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		extractEmail();
		emailToConfluence();
		executorService.shutdown();
		System.out.println("cost:" + (System.currentTimeMillis() - start));
	}

	private static void emailToConfluence() {
		while(true) {
			String str = queue.poll();
			if (str == null)
				break;
			executorService.execute(new EmailToConfluenceTask(str));
		}
	}

	static class EmailToConfluenceTask implements Runnable {

		String email;

		public EmailToConfluenceTask(String email) {
			this.email = email;
		}

		@Override
		public void run() {
			System.out.println(email);
			SleepUtils.millisecond(5);
		}
	}

	private static void extractEmail() {
		for (int i = 0; i < 1000; i++) {
			try {
				queue.put(String.valueOf(i));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("add:" + i);
			SleepUtils.millisecond(5);
		}
	}
}
