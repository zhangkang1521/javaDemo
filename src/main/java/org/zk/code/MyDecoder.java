package org.zk.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyDecoder extends ByteToMessageDecoder {

    // 入站，放到业务处理前
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Integer id = in.readInt();
        System.out.println("解码" + id);
        Protocol protocol = new Protocol();
        protocol.setId(id);
        out.add(protocol);
    }
}
