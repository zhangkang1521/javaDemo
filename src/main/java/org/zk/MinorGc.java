package org.zk;

/**
 * -Xms20m -Xmx20m -Xmn10m  -XX:+PrintGCDetails
 */
public class MinorGc {

	public static final int _1MB = 1024 * 1024;

	public static void main(String[] args) throws Exception {
		byte[] a, b, c, d;
		a = new byte[2 * _1MB];
		b = new byte[2 * _1MB];
		c = new byte[2 * _1MB];
		d = new byte[4 * _1MB]; // 发生一次MinorGc，将a,b,c移入老年代，d进入Eden区
		System.in.read();
	}
}
