package org.zk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class RedisDelayQueue<T> {

	Logger logger = LoggerFactory.getLogger(RedisDelayQueue.class);
	private JedisPool jedisPool;
	private final String queueName;

	public RedisDelayQueue(JedisPool jedisPool, String queueName) {
		this.jedisPool = jedisPool;
		this.queueName = queueName;
	}

	public void put(String data, long timeout) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.zadd(queueName, System.currentTimeMillis() + timeout, data);
		} finally {
			jedis.close();
		}
	}

	public String take() {
		Jedis jedis = jedisPool.getResource();
		try {
//			logger.debug("zrange");
			Set<String> set = jedis.zrangeByScore(queueName, 0, System.currentTimeMillis());
//			logger.debug("zrange result {}", set);
			if (set == null || set.isEmpty()) {
				return null;
			}
			String data = set.iterator().next();
			if (jedis.zrem(queueName, data) > 0) {
				return data;
			}
		} finally {
			jedis.close();
		}
		return null;
	}
}
