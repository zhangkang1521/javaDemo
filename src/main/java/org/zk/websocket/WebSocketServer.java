package org.zk.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.zk.http.HttpEchoHandler;

/**
 * @author zhangkang
 * @date 2025/1/5 19:35
 */
@Slf4j
public class WebSocketServer {

    ServerBootstrap serverBootstrap = new ServerBootstrap();

    @SneakyThrows
    public void start(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new HttpRequestDecoder());
                    ch.pipeline().addLast(new HttpObjectAggregator(65535));
                    ch.pipeline().addLast(new HttpResponseEncoder());
                    ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws", "echo", true, 10 * 1024));
                    // 自定义处理器
                    ch.pipeline().addLast(new TextWebSocketFrameHandler());
                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            log.info("服务器启动成功 {}", port);
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new WebSocketServer().start(9999);
    }

}
