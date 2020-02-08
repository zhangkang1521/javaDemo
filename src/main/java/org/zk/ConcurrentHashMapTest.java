package org.zk;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {
	public static void main(String[] args) {
		ConcurrentHashMap hashMap = new ConcurrentHashMap();
		hashMap.put(0, "111");
		hashMap.get(0);
	}
}
