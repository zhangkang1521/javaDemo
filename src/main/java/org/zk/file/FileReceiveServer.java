package org.zk.file;

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
public class FileReceiveServer {

    @SneakyThrows
    public static void main(String[] args) {
        int step = 1;
        long receivedLen = 0;
        long fileLen = 0;

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
                    // 文件长度
                    int len = socketChannel.read(byteBuffer);
                    byteBuffer.flip();
                    if (step == 1) {
                        fileLen = byteBuffer.getLong();
                        step = 2;
                    } else if (step == 2) {
                        receivedLen += len;
                    }

                    if (len == -1) {
                        selectionKey.cancel();
                        socketChannel.close();
                        log.info("channel close {}", socketChannel);
                    } else {
                        log.info("read data:{}", new String(byteBuffer.array(), 0, len));
                    }
                }
                it.remove();
            }
        }
    }
}
