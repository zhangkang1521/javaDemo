package org.zk;

import io.rebloom.client.Client;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BloomTest2 {

	static char[] chars = new char[26*2];
	static Random random = new Random();

	static {
		for (int i = 0; i < 26; i++) {
			chars[i] = (char)('a' + i);
		}
		for (int i = 0; i < 26; i++) {
			chars[26 + i] = (char)('A' + i);
		}
	}

	public static void main(String[] args) {
		final String KEY = "bloom2";
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.127.128");
		Client bloomClient = new Client(pool);
		bloomClient.delete(KEY);
		bloomClient.createFilter(KEY, 100000, 0.001);
		List<String> addList = randomStringList();
		List<String> notAddList = randomStringList();
		for (String str:addList) {
			bloomClient.add(KEY, str);
		}
		int count = 0;
		for (String str: notAddList) {
			if (bloomClient.exists(KEY, str)) {
				count++;
			}
		}
		System.out.println(count);
	}

	private static List<String> randomStringList() {
		final int LIST_SIZE = 100000;
		List<String> list = new ArrayList<>(LIST_SIZE);
		for(int i = 0; i < LIST_SIZE; i++) {
			list.add(randomString());
		}
		return list;
	}

	private static String randomString() {
		final int STRING_LEN = 30;
		StringBuilder sb = new StringBuilder(STRING_LEN);
		for (int i = 0; i < STRING_LEN; i++) {
			sb.append(chars[random.nextInt(chars.length)]);
		}
		return sb.toString();
	}
}
