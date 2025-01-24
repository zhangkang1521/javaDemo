package org.zk.netty;

import cn.hutool.core.io.resource.ResourceUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import lombok.extern.slf4j.Slf4j;
import org.zk.codec.Byte2IntegerCodec;
import org.zk.codec.IntegerDuplexHandler;
import org.zk.decoder.Byte2IntegerDecoder;
import org.zk.encoder.Integer2ByteEncoder;
import org.zk.protobuf.MsgProtos;
import org.zk.ssl.SSLContextHelper;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

@Slf4j
public class NettyServer {

    // 启动引导类，用于组装EventLoopGroup，Channel，初始化配置等
    ServerBootstrap serverBootstrap = new ServerBootstrap();

    /**
     * NioEventLoopGroup: 相当于netty的Reactor，里面会有一个线程循环select，有事件发生会进行分发
     * ChannelHandler: 区分入站（读取数据），出站（写入数据）
     * Pipeline: 内部是一个双向循环链表，串联ChannelHandle，入站从头部开始处理，出站从尾部开始处理
     * 父通道：NioServerSocketChannel，用于监听新连接
     * 子通道：父通道accept得到的NioSocketChannel，用于传输数据
     *
     * @param port
     * @throws Exception
     */
    public void start(int port) throws Exception {
        // boss，用于Accept新的链接，一个线程即可
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // work，用于读写请求，分发到处理的pipeline，默认cpu数量*2
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 设置事件循环组（多线程Reactor）
            serverBootstrap.group(bossGroup, workerGroup);
            // 设置通道类型（异步非阻塞服务端监听通道）
            serverBootstrap.channel(NioServerSocketChannel.class);
            // 设置子通道pipeline（主通道即NioServerSocketChannel，子通道为Accept得到）
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

                // ChannelInitializer是一个入站处理器，initChannel方法在handlerAdded触发时被调用，
                // 最终会在pipeline中移除自己，所以只会被调用一次
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    // 向子通道添加Handler（每建立一个新连接都会被回调初始化Handler）
                    ch.pipeline().addLast(
//                            new StringDecoder(), // 入站-解码
//                            new StringEncoder(), // 出站-编码
//                            new StringReplayDecoder(),
//                            new StringIntegerHeaderDecoder(),
//                            new Byte2IntegerDecoder(), // 入站-解码
//                            new Integer2ByteEncoder(), // 出站-编码
//                            new Byte2IntegerCodec(),
//                            new IntegerDuplexHandler(),
//                            createSslHandler(), // 安全加密
                            new ProtobufVarint32FrameDecoder(), // 解码length+data -> data
                            new ProtobufDecoder(MsgProtos.Msg.getDefaultInstance()), // 解码ByteBuf到Msg
                            new ProtobufVarint32LengthFieldPrepender(), // 编码，加上length
                            new ProtobufEncoder(),  // 编码，Msg到ByteBuf
                            SimpleServerHandler.INSTANCE); // 入站业务处理
                }
            });
            // 设置bytebuf分配方式
            serverBootstrap.option(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT);
            // serverBootstrap.childOption(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT);
            // bind添加异步任务，返回Future, sync等待异步任务处理完成
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            log.info("服务器启动成功 {}", port);
            // 阻塞当前程序，直到服务端关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅关闭EventLoopGroup，释放资源，线程
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private SslHandler createSslHandler() throws Exception {
        final String KEYSTORE_FILE = ResourceUtil.getResource("jks/one-way-auth/server.jks").getPath();
        SSLContext sslContext = SSLContextHelper.createSslContext("123456", KEYSTORE_FILE);
        SSLEngine sslEngine = sslContext.createSSLEngine();
        sslEngine.setUseClientMode(false);
        sslEngine.setNeedClientAuth(false);
        return new SslHandler(sslEngine);
    }

    public static void main(String[] args) throws Exception {
        new NettyServer().start(8888);
    }
}
