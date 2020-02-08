package org.zk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public class ArrayBlockingQueueTest {

	static LinkedBlockingDeque queue = new LinkedBlockingDeque<>();

	public static void main(String[] args) throws Exception {
		queue.addFirst(1);
		queue.addFirst(2);
		queue.addFirst(3);
		System.out.println(queue.removeFirst());
		System.out.println(queue.removeFirst());
		System.out.println(queue.removeFirst());
		System.out.println("ok");

//		queue.put(2);

//		queue.put(4);


	}



	static class Job implements Runnable {
		@Override
		public void run() {
			try {
				SleepUtils.second(3);
				System.out.println("take:" + queue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
