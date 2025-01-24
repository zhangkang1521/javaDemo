package org.zk.nio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author zhangkang
 * @date 2024/11/12 16:11
 */
@Slf4j
public class NioServer {

    @SneakyThrows
    public static void main(String[] args) {
        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8888));
        log.info("服务器启动");

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (selector.select() > 0) {
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey selectionKey = it.next();
                if (selectionKey.isAcceptable()) {
                    // ServerSocketChannel serverSocketChannel2 = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    log.info("accept {}", socketChannel);
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    log.info("register read {}", socketChannel);
                } else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    byteBuffer.clear();
                    int len = socketChannel.read(byteBuffer);
                    byteBuffer.flip();
                    if (len == -1) {
                        selectionKey.cancel();
                        socketChannel.close();
                        log.info("channel close {}", socketChannel);
                    } else {
                        log.info("read data:{}", new String(byteBuffer.array(), 0, len));
                        // log.info("position:{} limit:{}", byteBuffer.position(), byteBuffer.limit());
                        socketChannel.write(byteBuffer);
                    }
                }
                it.remove();
            }
        }
    }
}
