package org.zk;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClusterTest {

	JedisCluster jedisCluster;

	@Before
	public void before() {
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.127.132", 7000));
		nodes.add(new HostAndPort("192.168.127.132", 7001));
		nodes.add(new HostAndPort("192.168.127.132", 7002));
		nodes.add(new HostAndPort("192.168.127.132", 7003));
		nodes.add(new HostAndPort("192.168.127.132", 7004));
		nodes.add(new HostAndPort("192.168.127.132", 7005));
		jedisCluster = new JedisCluster(nodes);
	}


	@Test
	public void testGetSet() {
		jedisCluster.set("b", "22");
		System.out.println(jedisCluster.get("b"));
	}

	// No way to dispatch this command to Redis Cluster because keys have different slots
	@Test
	public void testMget() {
		jedisCluster.set("a", "1");
		jedisCluster.set("b", "2");
		List list = jedisCluster.mget("a", "b");
		System.out.println(list);
	}

	// 当一个key包含 {} 的时候，就不对整个key做hash，而仅对 {} 包括的字符串做hash
	@Test
	public void testMgetHash() {
		jedisCluster.set("a{0}", "1");
		jedisCluster.set("b{0}", "2");
		// jedisCluster.mset("a{0}", "1", "b{0}", "2");
		List list = jedisCluster.mget("a{0}", "b{0}");
		System.out.println(list);
	}

	@Test
	public void testKeys() {
		// JedisCluster only supports KEYS commands with patterns containing hash-tags
		// System.out.println(jedisCluster.keys("*")); // error
		System.out.println(jedisCluster.keys("*{0}")); // ok
	}
}
