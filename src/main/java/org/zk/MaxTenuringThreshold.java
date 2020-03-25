package org.zk;

/**
 * -Xms20m -Xmx20m -Xmn10m -XX:MaxTenuringThreshold=1  -XX:+PrintGCDetails
 */
public class MaxTenuringThreshold {

	public static final int _1MB = 1024 * 1024;

	public static void main(String[] args) {
		byte[] a, b, c, d;
		a = new byte[_1MB/4];
		b = new byte[4 * _1MB];
		c = new byte[4 * _1MB]; // 第一次MinorGC，将b移入老年代，c进入eden， a还在eden
//		d = new byte[4 * _1MB]; // 第二次MinorGC，将c,a移入老年代，d进入eden，a超过1岁，所以进入老年代

	}
}
