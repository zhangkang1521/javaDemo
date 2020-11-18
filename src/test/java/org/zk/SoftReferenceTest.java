package org.zk;

import java.lang.ref.SoftReference;

/**
 * -Xms20m -Xmx20m -Xmn10m
 */
public class SoftReferenceTest {

	public static void main(String[] args) {
		SoftReference<byte[]> softReference = new SoftReference<byte[]>(new byte[1024*1024*9]);
		System.gc(); // 足够，不回收
		System.out.println(softReference.get());
		System.gc(); // 足够，不回收
		System.out.println(softReference.get());

		byte[] bytes = new byte[1024 * 1024 * 9]; // 内存空间不足会被回收
		System.gc();
		System.out.println(softReference.get());
	}
}
