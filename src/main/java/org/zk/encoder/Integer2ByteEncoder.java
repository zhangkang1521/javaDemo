package org.zk.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangkang
 * @date 2024/12/8 19:58
 */
@Slf4j
public class Integer2ByteEncoder extends MessageToByteEncoder<Integer> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Integer msg, ByteBuf out) throws Exception {
        // channel.write(100) 这种才会触发
        // channel.write(byteBuf) 这种不会触发
        log.info("出站编码，将int编码为ByteBuf {}", msg);
        out.writeInt(msg);
    }
}
