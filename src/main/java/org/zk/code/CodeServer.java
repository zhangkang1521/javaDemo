package org.zk.code;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class CodeServer {

	public static void main(String[] args) throws Exception {
		new CodeServer().start(8888);
	}

	public void start(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline()
									// 发送请求，出站编码，这个位置无所谓
									.addLast(new MyEncoder())
									// 收到请求，入站先解码，必须放到业务处理Handler前面
									.addLast(new MyDecoder())
									// 业务处理Handler
									.addLast(new CodeServerHandler());
						}
					});

			ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
			System.out.println("服务端启动" + port);
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
