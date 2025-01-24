package org.zk.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.zk.protobuf.MsgProtos;

@Slf4j
public class SimpleClientHandler extends ChannelInboundHandlerAdapter {


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		MsgProtos.Msg resp = (MsgProtos.Msg)msg;
		log.info("收到服务器消息 {}", resp);
//		ByteBuf byteBuf = (ByteBuf) msg;
//		byte[] result1 = new byte[byteBuf.readableBytes()];
//		byteBuf.readBytes(result1);
//		log.info("收到服务器消息:{}", new String(result1));
	}


}
