package org.zk;

import java.util.Date;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {

	static Semaphore semaphore = new Semaphore(2, true);

	public static void main(String[] args) throws Exception{
		new Thread(new Task()).start();
		new Thread(new Task()).start();
		new Thread(new Task()).start();
	}

	static class Task implements Runnable {

		@Override
		public void run() {
			try {
				semaphore.acquire();
				System.out.println(new Date() + " ok ");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				semaphore.release();
			}
		}
	}




}
