package org.zk.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.zk.netty.SimpleClientHandler;
import org.zk.protobuf.MsgProtos;

@Slf4j
public class NettyClient {

    Bootstrap bootstrap = new Bootstrap();

    Channel channel;

    public NettyClient(String host, int port) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast();
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            log.info("连接服务器成功");
            channel = channelFuture.channel();
            channel.closeFuture().sync();
            log.info("客户端退出");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyClient("localhost", 8888);
    }


}
