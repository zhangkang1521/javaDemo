package org.zk;

/**
 * -XX:+PrintGCDetails
 */
public class Gc {

	private byte[] data = new byte[2*1024*1024];

	public static void main(String[] args) {
		Gc gc = new Gc();
		gc = null;
		System.gc();
	}
}
