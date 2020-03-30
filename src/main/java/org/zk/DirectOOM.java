package org.zk;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 堆外内存
 * -XX:MaxDirectMemorySize=100M
 */
public class DirectOOM {
	public static void main(String[] args) {
		while (true) {
			ByteBuffer byteBuffer = ByteBuffer.allocate(10 * 1024 * 1024);
			List
		}
	}
}
