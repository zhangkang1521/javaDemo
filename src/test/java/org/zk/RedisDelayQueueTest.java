package org.zk;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Date;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class RedisDelayQueueTest {

	static RedisDelayQueue redisDelayQueue;

	static {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.127.128", 6379);
		redisDelayQueue  = new RedisDelayQueue(pool, "delayQueue");
	}

	public static void main(String[] args) {
		System.out.println(new Date());
		redisDelayQueue.put("1", 3000);
		redisDelayQueue.put("2", 5000);
		new Thread(new Consumer()).start();
		new Thread(new Consumer()).start();
//		new Thread(new Consumer()).start();
	}

	static class Consumer implements Runnable{

		@Override
		public void run() {
			while(true) {
				String data = redisDelayQueue.take();
				if (data == null) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println(new Date() + " process: " + data);
				}
			}
		}
	}

}