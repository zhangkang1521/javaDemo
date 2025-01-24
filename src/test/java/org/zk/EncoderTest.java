package org.zk;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.zk.decoder.Byte2IntegerDecoder;
import org.zk.decoder.IntegerProcessHandler;
import org.zk.encoder.Integer2ByteEncoder;
import org.zk.encoder.String2IntegerEncoder;

/**
 * 出站编码
 * @author zhangkang
 * @date 2024/12/8 19:56
 */
@Slf4j
public class EncoderTest {

    @Test
    public void testIntEncoder() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new Integer2ByteEncoder());
            }
        });

        embeddedChannel.writeAndFlush(100);

        // 编码器已经将int转成byteBuf
        ByteBuf byteBuf = embeddedChannel.readOutbound();
        log.info("read:{}", byteBuf.readInt());
    }

    @Test
    public void testMessagetoMessageEncoder() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new Integer2ByteEncoder());
                ch.pipeline().addLast(new String2IntegerEncoder());
            }
        });

        embeddedChannel.writeAndFlush("0239fs");

        // 编码器已经将int转成byteBuf
        ByteBuf byteBuf = embeddedChannel.readOutbound();
        while (byteBuf.isReadable()) {
            log.info("read:{}", byteBuf.readInt());
        }
    }
}
