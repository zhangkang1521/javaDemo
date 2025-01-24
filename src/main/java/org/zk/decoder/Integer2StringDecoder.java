package org.zk.decoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 使用MessageToMessageDecoder将POJO转换为POJO
 * @author zhangkang
 * @date 2024/12/8 17:31
 */
@Slf4j
public class Integer2StringDecoder extends MessageToMessageDecoder<Integer> {
    @Override
    protected void decode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
        log.info("int to string");
        out.add(String.valueOf(msg));
    }
}
