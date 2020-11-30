package org.zk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SentinelTest {

	private static Logger log = LoggerFactory.getLogger(SentinelTest.class);

	public static void main(String[] args) throws Exception {
		Set sentinels = new HashSet();
		sentinels.add("192.168.127.132:26379");
		sentinels.add("192.168.127.132:26380");
		sentinels.add("192.168.127.132:26381");
		JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sentinels);

		for (int i = 0; i < 10000; i++) {
			Jedis jedis = null;
			try {
				jedis = sentinelPool.getResource(); // 返回master节点
				jedis.set("a", i + "");
				log.info("set and get ok :" + jedis.get("a"));
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace(); // master重新选举这一段时间会有报错，十几秒
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		}
	}
}
