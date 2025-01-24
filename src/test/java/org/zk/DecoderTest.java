package org.zk;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.compression.FastLzFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.junit.Test;
import org.zk.decoder.*;

import java.nio.charset.StandardCharsets;

/**
 * 入站编码
 * @author zhangkang
 * @date 2024/12/7 21:07
 */
public class DecoderTest {

    @Test
    public void testIntDecoder() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new Byte2IntegerDecoder());
                ch.pipeline().addLast(new IntegerProcessHandler());
            }
        });

        for (int i = 0; i < 10; i++) {
            ByteBuf byteBuf = Unpooled.buffer();
            byteBuf.writeInt(100 + i);
            embeddedChannel.writeInbound(byteBuf);
        }

    }

    @Test
    public void testAddIntDecoder() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new IntegerAddDecoder());
            }
        });

        for (int i = 0; i < 10; i++) {
            ByteBuf byteBuf = Unpooled.buffer();
            byteBuf.writeInt(i);
            embeddedChannel.writeInbound(byteBuf);
        }
    }

    @Test
    public void testStringIntegerDecoder() {
        // EmbeddedChannel 测试不出来半包问题，使用真实网络测试
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new StringIntegerHeaderDecoder());
            }
        });


        for (int i = 0; i < 1000; i++) {
            ByteBuf byteBuf = Unpooled.buffer();
            String msg = "特斯拉CEO马斯克身家已超3600亿美元" + i;
            byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
            byteBuf.writeInt(bytes.length);
            byteBuf.writeBytes(bytes);
            embeddedChannel.writeInbound(byteBuf);
        }
    }

    @Test
    public void testInt2StringDecoder() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new Byte2IntegerDecoder());
                ch.pipeline().addLast(new Integer2StringDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        });

        for (int i = 0; i < 10; i++) {
            ByteBuf byteBuf = Unpooled.buffer();
            byteBuf.writeInt(100 + i);
            embeddedChannel.writeInbound(byteBuf);
        }

    }

    @Test
    public void testFixedLengthDecoder() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new FixedLengthFrameDecoder(10));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        });

        for (int i = 0; i < 100; i++) {
            ByteBuf byteBuf = Unpooled.buffer();
            byteBuf.writeBytes(String.valueOf(i).getBytes(StandardCharsets.UTF_8));
            embeddedChannel.writeInbound(byteBuf);
        }
    }

    @Test
    public void testLineBasedDecoder() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        });

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            ByteBuf byteBuf = Unpooled.buffer();
            byteBuf.writeBytes(String.valueOf(ch).getBytes(StandardCharsets.UTF_8));
            if (ch == 'E' || ch == 'S' || ch == 'Z') {
                byteBuf.writeBytes("\r\n".getBytes(StandardCharsets.UTF_8));
            }
            embeddedChannel.writeInbound(byteBuf);
        }
    }

    @Test
    public void testDelimiter() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.wrappedBuffer("$$".getBytes())));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        });

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            ByteBuf byteBuf = Unpooled.buffer();
            byteBuf.writeBytes(String.valueOf(ch).getBytes(StandardCharsets.UTF_8));
            if (ch == 'E' || ch == 'S' || ch == 'Z') {
                byteBuf.writeBytes("$$".getBytes(StandardCharsets.UTF_8));
            }
            embeddedChannel.writeInbound(byteBuf);
        }
    }

    @Test
    public void testLengthFieldBasedFrameDecoder() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        });

        for (int i = 0; i < 20; i++) {
            ByteBuf byteBuf = Unpooled.buffer();
            byte[] bytes = ("hello" + i).getBytes(StandardCharsets.UTF_8);
            byteBuf.writeInt(bytes.length);
            byteBuf.writeBytes(bytes);

            embeddedChannel.writeInbound(byteBuf);
        }
    }

    @Test
    public void testLengthFieldBasedFrameDecoder2() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 2, 6));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        });

        ByteBuf byteBuf = Unpooled.buffer();
        byte[] bytes = ("hello").getBytes(StandardCharsets.UTF_8);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeShort(2); // version 2字节
        byteBuf.writeBytes(bytes);

        embeddedChannel.writeInbound(byteBuf);
    }

    @Test
    public void testLengthFieldBasedFrameDecoder3() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 2, 4, 4, 10));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        });

        ByteBuf byteBuf = Unpooled.buffer();
        byte[] bytes = ("hello").getBytes(StandardCharsets.UTF_8);
        byteBuf.writeChar('1'); // version 2字节
        byteBuf.writeInt(bytes.length); // length 4字节
        byteBuf.writeInt(2); // magic 4字节
        byteBuf.writeBytes(bytes);

        embeddedChannel.writeInbound(byteBuf);
    }
}
