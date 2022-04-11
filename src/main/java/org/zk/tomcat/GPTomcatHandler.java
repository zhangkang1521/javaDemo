package org.zk.tomcat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

public class GPTomcatHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof HttpRequest) {
			HttpRequest httpRequest = (HttpRequest)msg;
			System.out.println(httpRequest.method().name());
			System.out.println(httpRequest.uri());
			FullHttpResponse response = new DefaultFullHttpResponse(
					HttpVersion.HTTP_1_1,
					HttpResponseStatus.OK,
					Unpooled.wrappedBuffer("hello".getBytes(StandardCharsets.UTF_8)));
			response.headers().set("Content-Type", "text/html;");
			ctx.write(response);
			ctx.flush();
			ctx.close();
		}
	}

}
