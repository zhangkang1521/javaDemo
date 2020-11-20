package org.zk;

import java.util.concurrent.*;

public class ThreadExceptionTest {

	public static void main(String[] args) throws Exception {
		// 线程中的异常会抛出
//		new Thread(new Task()).start();

		// Future方式异常在future.get中抛出
//		ExecutorService executorService = Executors.newFixedThreadPool(1);
//		Future future = executorService.submit(new Task());
//		future.get();

		// execute方式，重写线程池afterExecute方法
		// ExecutorService executorService = new MyThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		// 使用ThreadFactory给线程设置UncaughtExceptionHandler
		ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new MyThreadFactory());
		executorService.execute(new Task());
		executorService.shutdown();
	}


	static class Task implements Runnable {

		@Override
		public void run() {
			// 线程池中的异常不会打印
			throw new RuntimeException("123");
		}
	}

	static class MyThreadFactory implements ThreadFactory {

		ThreadFactory threadFactory = Executors.defaultThreadFactory();

		@Override
		public Thread newThread(Runnable r) {
			Thread thread = threadFactory.newThread(r);
			thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread t, Throwable e) {
					if (e != null) {
						e.printStackTrace();
					}
				}
			});
			return thread;
		}
	}
}
