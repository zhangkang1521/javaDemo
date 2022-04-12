package org.zk.rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.zk.netty.SimpleClientHandler;
import org.zk.rpc.api.SayHelloService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcProxy {

    public static void main(String[] args) {
        SayHelloService sayHelloService = (SayHelloService) RpcProxy.create(SayHelloService.class);
        String result = sayHelloService.sayHello("zhangkang");
        System.out.println("返回结果" + result);
    }

    public static Object create(Class<?> clazz) {
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MethodProxy(clazz));
    }


    private static class MethodProxy implements InvocationHandler {

        private Class<?> clazz;

        private String host = "localhost";

        private int port = 20881;

        public MethodProxy(Class<?> clazz) {
            this.clazz = clazz;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return rpcInvoke(method, args);
        }

        public Object rpcInvoke(Method method, Object[] args) {
            InvokerProtocol invokerProtocol = new InvokerProtocol();
            invokerProtocol.setClassName(clazz.getName());
            invokerProtocol.setMethodName(method.getName());
            invokerProtocol.setParameterTypes(method.getParameterTypes());
            invokerProtocol.setValues(args);

            RpcProxyHandler rpcProxyHandler = new RpcProxyHandler();

            EventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(group)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline()
//                                    .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
//                                    .addLast(new LengthFieldPrepender(4))
                                        .addLast("encoder", new ObjectEncoder())
                                        .addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)))
                                        .addLast(rpcProxyHandler);
                            }
                        });
                Channel channel = bootstrap.connect(host, port).sync().channel();


                channel.writeAndFlush(invokerProtocol).sync();

                channel.closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                group.shutdownGracefully();
            }

            return rpcProxyHandler.getResponse();
        }
    }


}
