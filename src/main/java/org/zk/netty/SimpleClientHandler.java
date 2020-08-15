package org.zk.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SimpleClientHandler extends ChannelInboundHandlerAdapter {


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf byteBuf = (ByteBuf) msg;
		byte[] result1 = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(result1);
		System.out.println("收到服务器消息:" + new String(result1));
		byteBuf.release();
	}


}
