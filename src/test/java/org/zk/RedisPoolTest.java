package org.zk;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

public class RedisPoolTest {


	@Test
	public void execute() {
		// 自动归还资源到连接池
		RedisPool redisPool = new RedisPool();
		redisPool.execute(jedis -> {
			jedis.set("a", "111");
		});
	}

	@Test
	public void getValue() {
		RedisPool redisPool = new RedisPool();
		final ValueHolder<String> valueHolder = new ValueHolder<>();
		redisPool.execute(jedis -> {
			String str = jedis.get("a");
			valueHolder.setValue(str);
		});
		System.out.println(valueHolder.getValue());
	}

	static class ValueHolder<T> {
		private T value;

		public T getValue() {
			return value;
		}

		public void setValue(T value) {
			this.value = value;
		}
	}
}