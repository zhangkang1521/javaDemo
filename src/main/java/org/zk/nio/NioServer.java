package org.zk.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * 多路复用Server端
 */
public class NioServer {

	private ServerSocketChannel serverSocketChannel;
	private Selector selector;
	private ByteBuffer byteBufferRead = ByteBuffer.allocate(8);
	private ByteBuffer byteBufferWrite = ByteBuffer.allocate(8);


	public NioServer() throws Exception {
		// 创建多路复用器
		this.selector = Selector.open();
		// 创建服务器通道
		this.serverSocketChannel = ServerSocketChannel.open();
		this.serverSocketChannel.socket().bind(new InetSocketAddress(8888));
		this.serverSocketChannel.configureBlocking(false);
		// 将通道注册到多路复用器上，并注册监听事件
		this.serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
		System.out.println("开始监听8888");
	}

	public static void main(String[] args) throws Exception {
		new NioServer().start();
	}

	public void start() throws Exception {
		while (true) {
			this.selector.select(1000);
			Set<SelectionKey> selected = this.selector.selectedKeys();
			if (selected != null) {
				for (SelectionKey k : selected) {
					// 多路复用
					if (k.isAcceptable()) {
						processAccept(k);
					} else if (k.isReadable()) {
						processRead(k);
					}
				}
				selected.clear();
			}
		}
	}

	private void processAccept(SelectionKey k) throws Exception {
		SocketChannel sc = ((ServerSocketChannel) k.channel()).accept();
		if (sc != null) {
			System.out.println("收到一个新的连接：" + sc.socket().getRemoteSocketAddress());
			sc.configureBlocking(false); // 必须设置为非阻塞，否则报错
			// 将与客户端建立的通道注册到多路复用器，感兴趣读事件
			sc.register(this.selector, SelectionKey.OP_READ);
		}
	}

	private void processRead(SelectionKey k) throws Exception {
		while (byteBufferRead.hasRemaining()) {
			SocketChannel socketChannel = (SocketChannel) k.channel();
			int readSize = socketChannel.read(this.byteBufferRead);
			if (readSize > 0) {
				long readOffset = this.byteBufferRead.getLong(0);
				this.byteBufferRead.flip();
				System.out.println("服务器读取到：" + socketChannel.getRemoteAddress() + " " + readOffset);
				// 写给客户端
				byteBufferWrite.position(0);
				byteBufferWrite.putLong(readOffset * 10);
				byteBufferWrite.flip();
				socketChannel.write(this.byteBufferWrite);
			} else {
				break;
			}

		}
	}


}
