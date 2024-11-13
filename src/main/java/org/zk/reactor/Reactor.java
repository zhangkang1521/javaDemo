package org.zk.reactor;

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
 * 单线程Reactor
 *
 * @author zhangkang
 * @date 2024/11/13 15:14
 */
@Slf4j
public class Reactor {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    @SneakyThrows
    public Reactor() {
        this.selector = Selector.open();

        this.serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8888));
        log.info("服务器启动");

        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        selectionKey.attach(new AcceptHandle());
    }

    @SneakyThrows
    public void start() {
        while (true) { // 这里为啥需要循环，否则会退出？
            while (selector.select() > 0) {
                log.info("***");
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey selectionKey = it.next();
                    dispatch(selectionKey);
                    it.remove();
                }
            }
            log.info("============================");
        }
    }

    private void dispatch(SelectionKey selectionKey) {
        Handle handle = (Handle) selectionKey.attachment();
        // 这里可能是AcceptHandle、EchoHandle
        handle.process();
    }

    class AcceptHandle implements Handle {

        @Override
        @SneakyThrows
        public void process() {
            SocketChannel socketChannel = serverSocketChannel.accept();
            log.info("accept {}", socketChannel);
            new EchoHandle(socketChannel, selector);
        }
    }

    class EchoHandle implements Handle {

        SocketChannel socketChannel;
        Selector selector;
        SelectionKey selectionKey;
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        @SneakyThrows
        public EchoHandle(SocketChannel socketChannel, Selector selector) {
            this.socketChannel = socketChannel;
            this.selector = selector;
            this.socketChannel.configureBlocking(false);
            this.selectionKey = this.socketChannel.register(selector, 0);
            this.selectionKey.attach(this);
            this.selectionKey.interestOps(SelectionKey.OP_READ);
            this.selector.wakeup();
        }

        @Override
        @SneakyThrows
        public void process() {
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
                socketChannel.write(byteBuffer);
            }
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        new Reactor().start();
    }
}
