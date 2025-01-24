package org.zk.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zhangkang
 * @date 2024/12/7 21:07
 */
@Slf4j
public class Byte2IntegerDecoder extends /*ByteToMessageDecoder*/ ReplayingDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//        if (in.readableBytes() >= 4) {
            // ReplayingDecoderByteBuf 会自动判断长度
            int i = in.readInt();
            log.info("入站解码为int：{}", i);
            // 传给下一个handler，已经编码为int类型
            out.add(i);
    //        }
    }
}
