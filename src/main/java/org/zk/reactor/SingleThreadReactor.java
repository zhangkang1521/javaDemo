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
 * 1. 启动时注册感兴趣Accept事件，attach AcceptHandle；当有事件发生，分发到Handle处理
 * 2. AcceptHandle 接收新连接，感兴趣Read事件, attach EchoHandle
 * 3. EchoHandle 处理读写事件，来回切换
 * @author zhangkang
 * @date 2024/11/13 15:14
 */
@Slf4j
public class SingleThreadReactor {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    @SneakyThrows
    public SingleThreadReactor() {
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
        while (true) { // 这里与之前写法不一样，因为wakeup之后select()返回值为0，没有selectKey
            selector.select();
            // log.info("select() {}", size);
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey selectionKey = it.next();
                dispatch(selectionKey);
                it.remove();
            }
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
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        static final int RECIEVING = 0, SENDING = 1;
        int state = RECIEVING;

        @SneakyThrows
        public EchoHandle(SocketChannel socketChannel, Selector selector) {
            this.socketChannel = socketChannel;
            this.selector = selector;
            this.socketChannel.configureBlocking(false);
            // 这里不直接注册读事件，而等到attach之后再感兴趣读事件的原因？
            this.selectionKey = this.socketChannel.register(selector, 0);
            this.selectionKey.attach(this);
            this.selectionKey.interestOps(SelectionKey.OP_READ);
            // this.selector.wakeup(); // select()方法会立即返回
        }

        @Override
        @SneakyThrows
        public void process() {
            if (state == RECIEVING) {
                byteBuffer.clear();

//                int len = 0;
//                while ((len = socketChannel.read(byteBuffer)) > 0) {
//                    log.info("read data:{}", new String(byteBuffer.array(), 0, len));
//                }
//                byteBuffer.flip();
//                state = SENDING;
//                this.selectionKey.interestOps(SelectionKey.OP_WRITE);

                // 一次发送超过缓冲区，下一次会读取到，
                // 改为上面的循环读取，一次也只能读取缓冲区大小的数据
                int len = socketChannel.read(byteBuffer);
                byteBuffer.flip();
                if (len == -1) {
                    selectionKey.cancel();
                    socketChannel.close();
                    log.info("channel close {}", socketChannel);
                } else {
                    log.info("read data:{}", new String(byteBuffer.array(), 0, len));
                    state = SENDING;
                    this.selectionKey.interestOps(SelectionKey.OP_WRITE);
                }
            } else if (state == SENDING) {
                log.info("send data:{}", new String(byteBuffer.array(), 0, byteBuffer.limit()));
                socketChannel.write(byteBuffer);
                state = RECIEVING;
                this.selectionKey.interestOps(SelectionKey.OP_READ);
            }
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        new SingleThreadReactor().start();
    }
}
