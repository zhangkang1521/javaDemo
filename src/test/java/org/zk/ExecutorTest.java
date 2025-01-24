package org.zk;

import com.google.common.util.concurrent.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
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

	@Test
	@SneakyThrows
	public void testFuture() {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		log.info("submit");
		Future<String> future = executor.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(3000);
				return "test";
			}
		});
		// 阻塞，等待结果
		String result = future.get();
		log.info("result:{}", result);
	}

	@Test
	@SneakyThrows
	public void testListenerExecutor() {
		// 支持回调的线程池
		ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
		log.info("submit");
		ListenableFuture<String> future = executor.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				log.info("start process");
				Thread.sleep(3000);
				log.info("end process");
				return "test";
			}
		});
		Futures.addCallback(future, new FutureCallback<String>() {

			@Override
			public void onSuccess(String s) {
				// 线程池线程执行
				log.info("success {}", s);
			}

			@Override
			public void onFailure(Throwable throwable) {
				log.error("fail", throwable);
			}
		});

		Thread.sleep(5000);
	}
}
