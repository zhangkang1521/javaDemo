package org.zk.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

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
		ctx.write(encoded);
		ctx.flush();
	}

}
