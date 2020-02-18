package org.zk;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class ZipListTest {

	public static void main(String[] args) {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.127.128");
		Jedis jedis = pool.getResource();
		jedis.del("a", "b");
		for (int i = 0; i < 513; i++)
			jedis.hset("a", String.valueOf(i), "1");
		jedis.hset("b", "1", "1");
		System.out.println(jedis.objectEncoding("a"));  // 超过512标准结构存储
		System.out.println(jedis.objectEncoding("b"));  // 压缩储存
	}
}
