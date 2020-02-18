package org.zk;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class PfTest {

	public static void main(String[] args) {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.127.128");
		Jedis jedis = pool.getResource();
		// 网站uv统计
		for (int i = 0; i < 1000; i++)
			jedis.pfadd("page-2", "user-" + i);
		System.out.println(jedis.pfcount("page-2"));
	}
}
