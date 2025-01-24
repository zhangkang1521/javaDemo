package org.zk.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 编解码器（继承方式）
 * @author zhangkang
 * @date 2024/12/8 21:10
 */
@Slf4j
public class Byte2IntegerCodec extends ByteToMessageCodec<Integer> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Integer msg, ByteBuf out) throws Exception {
        log.info("出站编码 {}", msg);
        out.writeInt(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Integer msg = in.readInt();
        log.info("入站解码 {}", msg);
        out.add(msg);
        // 这里不用写这句，否则会触发2次入站解码
        // ctx.fireChannelRead(msg);
    }
}
