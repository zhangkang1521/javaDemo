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
		super.write(ctx, msg, promise);
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
				String response = "echo:";
				ByteBuf encoded = ctx.alloc().buffer();
				encoded.writeBytes(response.getBytes());
				// 是OubBound事件只有A
				// 会从当前的hander往前找第一个outbound来执行
//				ctx.writeAndFlush(encoded);
				// OutBound事件会C -> B -> A 进行传播
				// 每次都会从tail往前找第一个是outbound的handler来执行。
				// 一般用这个
				ctx.channel().writeAndFlush(encoded);
			}
		}, 3, TimeUnit.SECONDS);
	}
}
