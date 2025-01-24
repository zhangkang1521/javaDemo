package org.zk;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.zk.protobuf.MsgProtos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author zhangkang
 * @date 2024/12/10 10:01
 */
@Slf4j
public class ProtoBufTest {

    @Test
    @SneakyThrows
    public void test1() {
        MsgProtos.Msg msg = MsgProtos.Msg.newBuilder()
                .setId(1)
                .setContent("zk")
                .build();
        byte[] bytes = msg.toByteArray();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(bytes);

        MsgProtos.Msg msg1 = MsgProtos.Msg.parseFrom(baos.toByteArray());
        log.info("msg1:{}", msg1);
    }

    @Test
    @SneakyThrows
    public void test2() {
        MsgProtos.Msg msg = MsgProtos.Msg.newBuilder()
                .setId(1)
                .setContent("zk")
                .build();
        byte[] bytes = msg.toByteArray();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(bytes);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        // 从流中读取
        MsgProtos.Msg msg1 = MsgProtos.Msg.parseFrom(bais);
        log.info("msg1:{}", msg1);
    }

    @Test
    @SneakyThrows
    public void test3() {
        MsgProtos.Msg msg = MsgProtos.Msg.newBuilder()
                .setId(1)
                .setContent("zk")
                .build();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        msg.writeDelimitedTo(baos);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        // 加上字节长度，解决半包问题
        MsgProtos.Msg msg1 = MsgProtos.Msg.parseDelimitedFrom(bais);
        log.info("msg1:{}", msg1);
    }


    @Test
    public void testChannel() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                // length+content 解码content为Bytebuf
                ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                // 解码 ByteBuf转化为Msg
                ch.pipeline().addLast(new ProtobufDecoder(MsgProtos.Msg.getDefaultInstance()));
                // 编码length+content
                ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                // 编码Msg转化为ByteBuf
                ch.pipeline().addLast(new ProtobufEncoder());
                // 业务处理
                ch.pipeline().addLast(new MsgProcessHandler());
            }
        });

        MsgProtos.Msg msg = MsgProtos.Msg.newBuilder()
                .setId(1)
                .setContent("zk")
                .build();
        embeddedChannel.writeInbound(msg);
    }

    static class MsgProcessHandler extends ChannelInboundHandlerAdapter {
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            MsgProtos.Msg msg1 = (MsgProtos.Msg) msg;
            log.info("MsgProcessHandler :{}", msg1);
        }
    }
}
