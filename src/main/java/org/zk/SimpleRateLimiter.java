package org.zk;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;
import java.util.UUID;

public class SimpleRateLimiter {

	private Jedis jedis;

	public SimpleRateLimiter(Jedis jedis) {
		this.jedis = jedis;
	}

	/**
	 * 限制某用户某个操作一定时间内不能超过多少次
	 * @param userId
	 * @param action
	 * @param period 单位s
	 * @param maxCount
	 * @return
	 */
	public boolean isLimit(String userId, String action, int period, int maxCount) {
		// 使用zset，key为userId+action,score为时间戳，保证member不重复
		// 根据zrangeByScore可以过滤出最近某个时间范围的数据
		String key = userId + action;
		long timeStamp = System.currentTimeMillis();
		System.out.println(timeStamp);
		Set set = jedis.zrangeByScore(key, timeStamp - period * 1000, timeStamp);
		System.out.println("size: " + set.size());
		if (set.size() >= maxCount) {
			return true;
		} else {
			jedis.zadd(key, timeStamp, UUID.randomUUID().toString()); // 保证value唯一即可
			jedis.expire(key, period);
			return false;
		}
	}
}
