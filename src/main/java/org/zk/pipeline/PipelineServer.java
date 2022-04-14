package org.zk.pipeline;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.zk.netty.SimpleServerHandler;

public class PipelineServer {

	public static void main(String[] args) throws Exception {
		new PipelineServer().start(8888);
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
							// A -> B -> C
							ch.pipeline()
									.addLast(new InboundHandlerA())
									.addLast(new InboundHandlerB())
									.addLast(new InboundHandlerC())
									.addLast(new OutboundHandlerA())
									.addLast(new OutboundHandlerB())
									.addLast(new OutboundHandlerC());
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
