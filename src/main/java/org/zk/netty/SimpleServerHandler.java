package org.zk.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * netty入站处理器， ChannelInboundHandler
 * ChannelInboundHandlerAdapter对接口所有方法都有默认实现，故继承他比较方便
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf byteBuf = (ByteBuf) msg;
		byte[] bytes = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(bytes);
		String str = new String(bytes);
		System.out.println("收到客户端消息：" + str);
		byteBuf.release();

		String response = "echo:" + str;
		ByteBuf encoded = ctx.alloc().buffer();
		encoded.writeBytes(response.getBytes());

		ctx.channel().writeAndFlush(encoded);
	}

}
