package org.zk;

import com.google.gson.annotations.SerializedName;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
public class RedisPoolTest {

	@Test
	public void testPool() throws Exception {
		final int TOTAL = 3;

		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(TOTAL);
		config.setMaxIdle(TOTAL);
		config.setMaxWaitMillis(3 * 1000);
		config.setTestOnBorrow(true);
		JedisPool jedisPool = new JedisPool(config, "127.0.0.1", 6379);

		List<Jedis> jedisList = new ArrayList<>();

		for (int i = 0; i < TOTAL; i++) {
			Jedis jedis = jedisPool.getResource();
			jedisList.add(jedis);
			// 如果这里归还，重新拿到的还是原来的
			log.info("add jedis {}", jedis);
		}

		for (int i = 0; i < jedisList.size(); i++) {
			Jedis jedis = jedisList.get(i);
			log.info("close {}", jedis);
			// 这里不是关闭连接，是归还连接池
			jedis.close();
		}

		// netstat -ano | findstr :6379

		System.in.read();

		jedisPool.close();
	}


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