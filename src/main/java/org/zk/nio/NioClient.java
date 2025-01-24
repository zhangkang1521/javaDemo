package org.zk.nio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author zhangkang
 * @date 2024/11/12 16:11
 */
@Slf4j
public class NioClient {

    private static SocketChannel socketChannel;

    private static Selector selector;

    @SneakyThrows
    public static void main(String[] args) {
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost", 8888));
        while (!socketChannel.finishConnect()) {
            log.info("connect...");
        }
        log.info("connect to server");


        // 接收服务端消息
        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
        new Thread(new Receive()).start();

        // 发送数据
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        log.info("please input(exit:退出)");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if ("exit".equals(line)) {
                socketChannel.close();
                break;
            }
            byteBuffer.put(line.getBytes(StandardCharsets.UTF_8));
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
        }
    }

    static class Receive implements Runnable {

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        @Override
        @SneakyThrows
        public void run() {
            while (selector.select() > 0) {
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey selectionKey = it.next();
                    if (selectionKey.isReadable()) {
                        byteBuffer.clear();
                        int len = socketChannel.read(byteBuffer);
                        byteBuffer.flip();
                        log.info("收到服务器数据 {}", new String(byteBuffer.array(), 0, len));
                    }
                    it.remove();
                }
            }
        }
    }
}
