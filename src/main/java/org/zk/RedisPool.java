package org.zk;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisPool {
	private JedisPool jedisPool;

	public RedisPool() {
		this.jedisPool = new JedisPool("192.168.127.128", 6379);
	}

	public void execute(CallWithJedis callWithJedis) {
		try(Jedis jedis = jedisPool.getResource()) {
			callWithJedis.call(jedis);
		}
	}
}
