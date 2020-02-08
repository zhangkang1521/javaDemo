package org.zk;

import java.util.HashMap;
import java.util.UUID;

public class HashMapDeadCycle {
	public static void main(String[] args) throws Exception{
		final HashMap hashMap = new HashMap();
		hashMap.put(0, 111);
		hashMap.put(17, 222);
//		Thread t = new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				for (int i = 0; i < 10000; i++) {
//					new Thread(new Runnable() {
//						@Override
//						public void run() {
//							hashMap.put(UUID.randomUUID().toString(), "");
//						}
//					}).start();
////					hashMap.put(UUID.randomUUID().toString(), "");
//				}
//			}
//		});
//		t.start();
//		t.join();
//		System.out.println(hashMap.size());
//		for (int i = 0; i < 1000; i++) {
//			System.out.println(hash(i));
//		}
	}

	static int hash(Object k) {
		int h = 0;
		if (0 != h && k instanceof String) {
			return sun.misc.Hashing.stringHash32((String) k);
		}

		h ^= k.hashCode();

		// This function ensures that hashCodes that differ only by
		// constant multiples at each bit position have a bounded
		// number of collisions (approximately 8 at default load factor).
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}
}
