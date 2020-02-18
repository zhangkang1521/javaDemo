package org.zk;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import static org.junit.Assert.*;

public class SimpleRateLimiterTest {

	Jedis jedis;

	@Before
	public void before() {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.127.128");
		jedis = pool.getResource();
	}

	@After
	public void after() {
		jedis.close();
	}



	@Test
	public void test() throws Exception {
		SimpleRateLimiter simpleRateLimiter = new SimpleRateLimiter(jedis);
		for (int i = 0; i < 6; i++) {
			boolean isLimit = simpleRateLimiter.isLimit("100", "reply", 10, 5);
			System.out.println(i + ": " + isLimit);
//			Thread.sleep(3000);
		}
	}



}