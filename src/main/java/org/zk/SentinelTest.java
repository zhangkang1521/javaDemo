package org.zk;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

public class SentinelTest {

	public static void main(String[] args) {
		Set sentinels = new HashSet();
		sentinels.add("192.168.127.128:26379");
		JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sentinels);
		Jedis jedis = sentinelPool.getResource(); // 返回master节点
		jedis.set("a", "1123");
		System.out.println(jedis.get("a"));
	}
}
