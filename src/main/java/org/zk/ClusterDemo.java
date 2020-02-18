package org.zk;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class ClusterDemo {

	public static void main(String[] args) {
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.127.128", 6379));
		nodes.add(new HostAndPort("192.168.127.128", 6380));
		nodes.add(new HostAndPort("192.168.127.128", 6381));
		nodes.add(new HostAndPort("192.168.127.128", 6382));
		nodes.add(new HostAndPort("192.168.127.128", 6383));
		nodes.add(new HostAndPort("192.168.127.128", 6384));
		JedisCluster jedisCluster = new JedisCluster(nodes);
//		jedisCluster.set("cc", "123456");
		System.out.println(jedisCluster.get("cc"));
	}
}
