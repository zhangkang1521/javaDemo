package org.zk;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueTest {
	public static void main(String[] args) {
		ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
		queue.offer(1);
		queue.offer(2);
		queue.offer(3);
		queue.offer(4);
		queue.poll();
		queue.poll();
		queue.poll();
		queue.poll();
	}
}
