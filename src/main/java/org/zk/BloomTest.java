package org.zk;

import io.rebloom.client.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class BloomTest {

	public static void main(String[] args) {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.127.128");
		Client bloomClient = new Client(pool);
		for (int i = 0; i < 100000; i++) {
			bloomClient.add("bloom", "user" + i);
			if (bloomClient.exists("bloom", "user" + (i+1))) {
				System.out.println(i);
				break;
			}
		}
	}
}
