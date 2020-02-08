package org.zk;

import org.junit.Test;

import java.util.concurrent.*;

public class FutureTest {

	public static void main(String[] args) throws Exception {
		// 使用线程执行
		final FutureTask futureTask = new FutureTask<>(new MyCallable());
		new Thread(futureTask).start(); // 新线程执行run,执行完成唤醒get阻塞的线程
		System.out.println(futureTask.get());


		// 使用ExecutorService返回FutureTask
//		ExecutorService executorService = Executors.newSingleThreadExecutor();
//		Future future = executorService.submit(new MyCallable()); // 自动新建FutureTask,然后线程池的线程执行FutureTask.run
//		System.out.println(future.get());


	}


	@Test
	public void useExecutor() throws Exception{

	}

	@Test
	public void submitRunnable() throws Exception{
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future future = executorService.submit(new MyRunnable());
		System.out.println(future.get()); // 返回null
	}

	static class MyCallable implements Callable<Integer> {
		@Override
		public Integer call() throws Exception {
			System.out.println(Thread.currentThread() + " start");
			SleepUtils.second(3);
			return 100;
		}
	}

	static class MyRunnable implements Runnable {
		@Override
		public void run() {
			System.out.println("run ...");
		}
	}
}
