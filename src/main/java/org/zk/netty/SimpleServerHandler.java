package org.zk.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.zk.protobuf.MsgProtos;

/**
 * netty入站处理器， ChannelInboundHandler
 * ChannelInboundHandlerAdapter对接口所有方法都有默认实现，故继承他比较方便
 */
@Slf4j
@ChannelHandler.Sharable
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 必须加上@ChannelHandler.Sharable，否则多个客户端连接时，会报异常
	 */
	public static final SimpleServerHandler INSTANCE = new SimpleServerHandler();

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof MsgProtos.Msg) {
			MsgProtos.Msg request = (MsgProtos.Msg) msg;
			log.info("收到客户端请求 {}", request);

			MsgProtos.Msg msgReply = MsgProtos.Msg.newBuilder()
					.setId(request.getId())
					.setContent("echo:" + request.getContent())
					.build();
			ctx.channel().writeAndFlush(msgReply);

//			ctx.writeAndFlush("echo " + request);
		}
		ctx.fireChannelRead(msg);

//		ByteBuf byteBuf = (ByteBuf) msg;
//		log.info("allocate:{}", byteBuf.alloc());
//		byte[] bytes = new byte[byteBuf.readableBytes()];
//		byteBuf.readBytes(bytes);
//		String str = new String(bytes);
//		log.info("收到客户端消息：{}", str);
//		// 2种方式释放byteBuf，手动释放或否由Netty自动释放
//		// byteBuf.release();
//		ctx.fireChannelRead(msg);
//
//		String response = "echo:" + str;
//		// 获取Channel配置的分配器


		// 不能手动释放，出站时pipeline中的HeadContext会自动释放，否则报错
//		writeBuf.release();
	}


	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		log.info("客户端注册 {}", ctx.channel().remoteAddress());
		super.channelRegistered(ctx);
	}
//
//	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		log.info("channelActive {}", ctx.channel());
//		super.channelActive(ctx);
//	}
//
//	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//		log.info("channelInactive {}", ctx.channel());
//		super.channelInactive(ctx);
//	}
//
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		log.info("客户端退出 {}", ctx.channel().remoteAddress());
		super.channelUnregistered(ctx);
	}



	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("通道异常", cause);
		super.exceptionCaught(ctx, cause);
	}


}
