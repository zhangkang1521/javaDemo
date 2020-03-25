package org.zk;

/**
 * -Xms20m -Xmx20m -Xmn10m -XX:MaxTenuringThreshold=15  -XX:+PrintGCDetails
 */
public class MaxTenuringThreshold2 {

	public static final int _1MB = 1024 * 1024;
	public static final int _1KB = 1024;

	public static void main(String[] args) {
		byte[] a, b, c, d, e;
		a = new byte[_1MB / 4];
		b = new byte[_1MB / 4];
		c = new byte[4 * _1MB];
		d = new byte[4 * _1MB]; // a,b进入survivor，达到66%
		e = new byte[4 * _1MB]; // survivor中同年龄的所有对象大小大于survivor空间的一半，进入老年代

	}
}
