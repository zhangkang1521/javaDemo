package org.zk.code;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class CodeClient {

	public static void main(String[] args) {
		new CodeClient("localhost", 8888);
	}

	public CodeClient(String host, int port) {
		// 开启线程池
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline()
									// 发送请求，出站编码，这个位置无所谓
									.addLast(new MyEncoder())
									// 收到请求，入站先解码，位置必须放到业务处理Handler前面
									.addLast(new MyDecoder())
									// 业务处理Handler
									.addLast(new CodeClientHandler());
						}
					});
			Channel channel = bootstrap.connect(host, port).sync().channel();


			Protocol protocol = new Protocol();
			protocol.setId(200);
			channel.writeAndFlush(protocol);

			channel.closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}


}
