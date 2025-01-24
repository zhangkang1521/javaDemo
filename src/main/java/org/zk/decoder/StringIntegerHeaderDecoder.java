package org.zk.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zhangkang
 * @date 2024/12/7 22:09
 */
@Slf4j
public class StringIntegerHeaderDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        // 先标记位置，后面数据不够，可以重置
        in.markReaderIndex();
        int length = in.readInt();
        if (in.readableBytes() < length) {
            // 重置
            in.resetReaderIndex();
            log.info("数据不够，等待");
            return;
        }
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        String msg = new String(bytes, "UTF-8");
        log.info("编码后：{}", msg);
        out.add(msg);
    }
}
