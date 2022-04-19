package org.zk.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyEncoder extends MessageToByteEncoder<Protocol> {

    // 出站被调用
    @Override
    protected void encode(ChannelHandlerContext ctx, Protocol msg, ByteBuf out) throws Exception {
        System.out.println("编码" + msg.getId());
        out.writeInt(msg.getId());
    }
}
