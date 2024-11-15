package org.zk.reactor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程Reactor
 * @author zhangkang
 * @date 2024/11/13 15:14
 */
@Slf4j
public class MultiThreadReactorServer {

    private Selector bossSelector;

    private Selector[] workSelectors = new Selector[2];

    private ServerSocketChannel serverSocketChannel;

    @SneakyThrows
    public MultiThreadReactorServer() {
        this.bossSelector = Selector.open();

        this.serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("localhost", 18899));
        log.info("服务器启动");

        SelectionKey selectionKey = serverSocketChannel.register(bossSelector, SelectionKey.OP_ACCEPT);
        selectionKey.attach(new AcceptHandle());

        this.workSelectors[0] = Selector.open();
        this.workSelectors[1] = Selector.open();


    }

    @SneakyThrows
    public void start() {
        new Thread(new Reactor(bossSelector), "boss").start();

        new Thread(new Reactor(workSelectors[0]), "work-0").start();
        new Thread(new Reactor(workSelectors[1]), "work-1").start();
    }

    /**
     * 负责遍历事件，分发
     */
    class Reactor implements Runnable {

        private Selector selector;

        public Reactor(Selector selector) {
            this.selector = selector;
        }

        @Override
        @SneakyThrows
        public void run() {
            while (!Thread.interrupted()) { // 这里与之前写法不一样，因为wakeup之后select()返回值为0，没有selectKey
                // 这里与之前有区别，如果不加超时，接收不到读事件
                selector.select(1000);
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey selectionKey = it.next();
                    // 改成线程池处理后，客户端发送一次，收到多个事件
                    log.info("事件发生 read:{} write:{}", selectionKey.isReadable(), selectionKey.isWritable());
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
    }



    class AcceptHandle implements Handle {

        private Random random = new Random();

        @Override
        @SneakyThrows
        public void process() {
            SocketChannel socketChannel = serverSocketChannel.accept();
            log.info("accept {}", socketChannel);
            // 有多个selector，随机选择一个
            new EchoHandle(socketChannel, workSelectors[random.nextInt(2)]);
        }
    }

    class EchoHandle implements Handle {

        SocketChannel socketChannel;
        Selector selector;
        SelectionKey selectionKey;
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        static final int RECIEVING = 0, SENDING = 1;
        int state = RECIEVING;

        ExecutorService executorService = Executors.newFixedThreadPool(4);

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
            // 线程池处理读写
            this.executorService.execute(new AsyncTask());
        }

        // 改成线程池后，要加上同步
        @SneakyThrows
        private synchronized void asyncRun() {
            if (state == RECIEVING) {
                byteBuffer.clear();
                int len = socketChannel.read(byteBuffer);
                log.info("读到长度 {}", len);
                byteBuffer.flip();
                if (len == -1) {
                    selectionKey.cancel();
                    socketChannel.close();
                    log.info("channel close {}", socketChannel);
                } else if (len > 0) {
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

        class AsyncTask implements Runnable {

            @Override
            public void run() {
                EchoHandle.this.asyncRun();
            }
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        new MultiThreadReactorServer().start();
    }
}
