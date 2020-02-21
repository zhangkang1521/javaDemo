package org.zk.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class GetChannel {

	public static void main(String[] args) throws Exception {
		FileChannel fc = new FileOutputStream("E:/data.txt").getChannel();
		fc.write(ByteBuffer.wrap("abc".getBytes()));
		fc.close();

		fc = new FileInputStream("E:/data.txt").getChannel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		fc.read(byteBuffer);
		byteBuffer.flip();
		while(byteBuffer.hasRemaining()) {
			System.out.println((char)byteBuffer.get());
		}
	}
}
