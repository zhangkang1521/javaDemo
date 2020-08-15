package org.zk.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NettyClient {

	public static void main(String[] args) {
		new NettyClient("localhost", 8888);
	}

	public NettyClient(String host, int port) {
		// 开启线程池
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new SimpleClientHandler());
						}
					});
			Channel channel = bootstrap.connect(host, port).sync().channel();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			for (;;) {
				String msg = bufferedReader.readLine();
				if("exit".equals(msg)) {
					break;
				}
				ByteBuf buf = Unpooled.buffer();
			    buf.writeBytes(msg.getBytes());
				channel.writeAndFlush(buf);
			}
			channel.closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}


}
