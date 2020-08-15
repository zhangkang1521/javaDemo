package org.zk.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Nio客户端
 */
public class NioClient {

	private Selector selector;
	private SocketChannel socketChannel;
	private final ByteBuffer byteBufferWrite = ByteBuffer.allocate(8);
	private ByteBuffer byteBufferRead = ByteBuffer.allocate(8);

	public NioClient() throws Exception {
		this.selector = Selector.open();
	}

	public static void main(String[] args) throws Exception {
		new NioClient().start();
	}

	private void start() throws Exception {
		connectMaster();
		long i = 100;
		while (true) {
			try {
				writeToServer(i++);
				processRead();
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	private void connectMaster() throws Exception {
		this.socketChannel = SocketChannel.open(new InetSocketAddress(8888));
		socketChannel.configureBlocking(false);
		if (this.socketChannel != null) {
			this.socketChannel.register(this.selector, SelectionKey.OP_READ);
		}
	}

	private void writeToServer(final long maxOffset) throws Exception {
		this.byteBufferWrite.position(0);
		this.byteBufferWrite.limit(8);
		this.byteBufferWrite.putLong(maxOffset);
		this.byteBufferWrite.position(0);
		this.byteBufferWrite.limit(8);
		this.socketChannel.write(this.byteBufferWrite);
		System.out.println("向服务器发送：" + maxOffset);
	}

	private void processRead() throws Exception {
		while (byteBufferRead.hasRemaining()) {
			int readSize = socketChannel.read(this.byteBufferRead);
			if (readSize > 0) {
				long readOffset = this.byteBufferRead.getLong(0);
				this.byteBufferRead.flip();
				System.out.println("客户端读取到：" + socketChannel.getRemoteAddress() + " " + readOffset);
			} else {
				break;
			}
		}
	}
}
