package org.zk;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.util.List;

public class TransactionDemo {

	public static void main(String[] args) {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.127.128");
		Jedis jedis = pool.getResource();
		jedis.set("balance", "10");
		jedis.watch("balance"); // 如果事务执行时被改变，导致事务失败

		jedis.set("balance", "11");
		Transaction transaction = jedis.multi();
		transaction.set("balance", "20");
		transaction.set("xx", "xx");
		List<Object> results = transaction.exec();
		System.out.println(results);

	}
}
