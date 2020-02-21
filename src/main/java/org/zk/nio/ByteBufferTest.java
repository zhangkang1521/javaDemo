package org.zk.nio;

import java.nio.ByteBuffer;

public class ByteBufferTest {
	public static void main(String[] args) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(20);
		byteBuffer.put((byte)'a');
		byteBuffer.put((byte)'b');
		byteBuffer.put((byte)'c');
		byteBuffer.flip();
		System.out.println(byteBuffer.get());
		System.out.println(byteBuffer.get());
		System.out.println(byteBuffer.get());
		System.out.println(byteBuffer.get());
	}
}
