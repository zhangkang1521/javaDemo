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


}
