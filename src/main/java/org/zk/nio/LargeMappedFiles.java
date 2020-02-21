package org.zk.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class LargeMappedFiles {

	public static void main(String[] args) throws Exception{
		MappedByteBuffer mappedByteBuffer = new RandomAccessFile("E:/data.txt", "rw").getChannel()
				.map(FileChannel.MapMode.READ_WRITE, 0, 10);
		mappedByteBuffer.put((byte)'a');
		mappedByteBuffer.put((byte)'b');
		System.out.println((char)mappedByteBuffer.get(1));
	}
}
