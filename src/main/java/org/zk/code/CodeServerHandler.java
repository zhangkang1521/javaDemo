package org.zk.code;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class CodeServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Protocol) {
			Protocol protocol = (Protocol) msg;
			protocol.setId(protocol.getId() + 1);
			ctx.channel().writeAndFlush(protocol);
		}
	}

}
