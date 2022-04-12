package org.zk.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class RegistryHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务端收到请求：" + msg);
        if (msg instanceof InvokerProtocol) {
            ctx.write("result");
            ctx.flush();
            ctx.close();
        }
    }
}
