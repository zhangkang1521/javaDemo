package org.zk.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.zk.codec.Byte2IntegerCodec;
import org.zk.codec.IntegerDuplexHandler;
import org.zk.decoder.Byte2IntegerDecoder;
import org.zk.encoder.Integer2ByteEncoder;
import org.zk.protobuf.MsgProtos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Slf4j
public class NettyClient {

    Bootstrap bootstrap = new Bootstrap();

    Channel channel;

    public NettyClient(String host, int port) {
        // 开启线程池
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap.group(group);
            // 通道类型：客户端通道
            bootstrap.channel(NioSocketChannel.class);
            // 服务端是配置childHandler，这里配置handler即可
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(
//                            new Byte2IntegerDecoder(), // 入站解码器
//                            new Integer2ByteEncoder(), // 出站编码器
                            // new Byte2IntegerCodec(),
//                            new IntegerDuplexHandler(),
                            new ProtobufVarint32FrameDecoder(),
                            new ProtobufDecoder(MsgProtos.Msg.getDefaultInstance()),
                            new ProtobufVarint32LengthFieldPrepender(),
                            new ProtobufEncoder(),
                            new SimpleClientHandler()); // 入站业务处理
                }
            });
            // 配置内存分配器(默认是池化的直接内存)
            // bootstrap.option(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT);
            // 连接服务器
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            log.info("连接服务器成功");

            channel = channelFuture.channel();

            MsgProtos.Msg msg = MsgProtos.Msg.newBuilder()
                    .setId(1)
                    .setContent("zkzkzk")
                    .build();
            log.info("发送消息:{}", msg);
            channel.writeAndFlush(msg);
            // 输入
            // new Thread(new Input()).start();
            Thread.sleep(200);
            channel.close().sync();

            // 等待通道关闭
            channel.closeFuture().sync();
            log.info("客户端退出");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    class Input implements Runnable {

        @Override
        @SneakyThrows
        public void run() {
            log.info("请输入(exit:退出): ");
//            Scanner scanner = new Scanner(System.in);
            //while (true) {

            // 出现粘包问题
//            for (int i = 0; i < 1000; i++) {
//                String message = "特斯拉CEO马斯克身家已超3600亿美元" + i;
//                if ("exit".equalsIgnoreCase(message)) {
//                    log.info("关闭通道");
//                    channel.close().sync();
//                    break;
//                }
                // 非池化的堆区内存
                //ByteBuf byteBuffer = Unpooled.buffer();
                //log.info("Unpooled.buffer() alloc:{} hasArray:{} isDirect:{}", byteBuffer.alloc(), byteBuffer.hasArray(),  byteBuffer.isDirect());
                // 默认池化的直接内存（推荐使用）
                //ByteBuf byteBuf = channel.alloc().buffer();
                // byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
                //byteBuf.writeInt(100);
                // log.info("channel alloc:{}  hasArray:{} isDirect:{}", byteBuf.alloc(), byteBuf.hasArray(),  byteBuf.isDirect());
                //byteBuf.writeBytes(bytes);
                channel.write(100);
                // 不能手动释放，出站时pipeline中的HeadContext会自动释放，否则报错
                // byteBuf.release();
//            }
        }
    }

    public static void main(String[] args) {
        new NettyClient("localhost", 8888);
    }


}
