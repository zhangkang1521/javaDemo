package org.zk.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

	// ServerBootstrap用于组装EventLoopGroup，Channel等
	ServerBootstrap serverBootstrap = new ServerBootstrap();

    public static void main(String[] args) throws Exception {
        new NettyServer().start(8888);
    }

    public void start(int port) throws Exception {
		// boss事件循环组，用于Accept新的链接，一个线程即可
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		// 工作事件循环组，用于读写请求，分发到处理的pipeline，默认cpu数量*2
		EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 设置事件循环组
            serverBootstrap.group(bossGroup, workerGroup);
            // 设置通道类型
            serverBootstrap.channel(NioServerSocketChannel.class);
            // 设置子通道pipeline（主通道即NioServerSocketChannel，子通道为Accept得到）
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                	// 向子通道添加Handler（每建立一个新连接都会被回调初始化Handler）
                    ch.pipeline().addLast(new SimpleServerHandler());
                }
            });
            // 设置端口
            serverBootstrap.localAddress(port);
            // bind添加异步任务，返回Future, sync等待异步任务处理完成
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            System.out.println("服务端启动" + port);
            // 等待通道关闭？
            channelFuture.channel().closeFuture().sync();
        } finally {
        	// 优雅关闭EventLoopGroup，释放资源，线程
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
