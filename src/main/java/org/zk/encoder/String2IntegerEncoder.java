package org.zk.encoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author zhangkang
 * @date 2024/12/8 21:04
 */
public class String2IntegerEncoder extends MessageToMessageEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        char[] array = msg.toCharArray();
        for (char ch : array) {
            if (ch >= '0' && ch <= '9') {
                out.add(ch - '0');
            }
        }
    }
}
