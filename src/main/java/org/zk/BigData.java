package org.zk;

/**
 * -Xms20m -Xmx20m -Xmn10m -XX:PretenureSizeThreshold=3145728 -XX:+PrintGCDetails
 */
public class BigData {
	public static void main(String[] args) {
		byte[] a = new byte[4*1024*1024]; // 直接进入老年代
	}
}
