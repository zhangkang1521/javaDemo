package org.zk.heartbeat;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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


    public NettyClient(String host, int port) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(); // 入站业务处理
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            log.info("连接服务器成功");

            for (int i = 0; i < 10; i++) {
                ByteBuf byteBuf = Unpooled.buffer();
                byteBuf.writeInt(999);
                log.info("send to server {}", i);
                channelFuture.channel().writeAndFlush(byteBuf);
                Thread.sleep(1000);
            }

            // 等待通道关闭
            channelFuture.channel().closeFuture().sync();
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
