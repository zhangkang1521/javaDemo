package org.zk.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.util.concurrent.TimeUnit;

public class OutboundHandlerB extends ChannelOutboundHandlerAdapter {

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		System.out.println("OutboundHandlerB write");
		ctx.write(msg, promise);
	}

	/**
	 * 本Handler被添加时调用
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("延迟3s write say hello");
		ctx.executor().schedule(new Runnable() {
			@Override
			public void run() {
				// 客户端能收到，但是OubBound事件只有A
//				String response = "echo:";
//				ByteBuf encoded = ctx.alloc().buffer();
//				encoded.writeBytes(response.getBytes());
//				ctx.write(encoded);
//				ctx.flush();
				// 客户端收不到，收不到是因为没有解码器，OubBound事件只有A
//				ctx.writeAndFlush("hello");
				// 客户端能收不到，但OutBound事件会C -> B -> A 进行传播
				ctx.channel().write("aa");
			}
		}, 3, TimeUnit.SECONDS);
	}
}
