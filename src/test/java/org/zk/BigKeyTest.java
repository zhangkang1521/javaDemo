package org.zk;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.*;

import java.util.List;
import java.util.Map;

public class BigKeyTest {

	Jedis jedis;

	@Before
	public void before() {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.127.132", 6379);
		jedis = pool.getResource();
	}

	@Test
	public void saveBigKey() {
		String _128B = new String(new byte[128]);
		for (int i = 0; i < 1000000; i++)
			jedis.hset("hash2", String.valueOf(i), _128B);
	}

	@Test
	public void deleteBigKey() {
		long startTime = System.currentTimeMillis();
		jedis.del("hash2");
		System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
	}

	@Test
	public void deleteBigKeyByScan() {
		long startTime = System.currentTimeMillis();
		String key = "hash2";
		String cursor = "0";
		while (true) {
			ScanResult<Map.Entry<String, String>> scanResult = jedis.hscan(key, cursor, new ScanParams().count(100));
			cursor = scanResult.getCursor();
			List<Map.Entry<String, String>> list = scanResult.getResult();
			if (list == null || list.isEmpty()) {
				continue;
			}
			String[] fields = list.stream().map(Map.Entry::getKey).toArray(String[]::new);
			jedis.hdel(key, fields);
			if ("0".equals(cursor)) {
				jedis.del(key);
				break;
			}

		}
		System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
	}
}
