package org.zk;

import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

public class Scan {

	public static void main(String[] args) {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.127.128");
		Jedis jedis = pool.getResource();
		ScanParams scanParams = new ScanParams();
		scanParams.match("a*");
		String cursor = "0";
		Set<String> keys = new HashSet<>();
		do {
			ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
			cursor = scanResult.getCursor();
			keys.addAll(scanResult.getResult());
		} while (!cursor.equals("0"));
		System.out.println(keys.size());
	}
}
