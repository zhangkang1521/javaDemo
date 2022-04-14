package org.zk.pipeline;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.zk.netty.SimpleClientHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PilelineClient {

	public static void main(String[] args) {
		new PilelineClient("localhost", 8888);
	}

	public PilelineClient(String host, int port) {
		// 开启线程池
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new ClientInboundHandler());
						}
					});
			Channel channel = bootstrap.connect(host, port).sync().channel();

			channel.closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}


}
