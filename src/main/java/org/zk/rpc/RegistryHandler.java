package org.zk.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.zk.rpc.api.SayHelloService;
import org.zk.rpc.api.SayHelloServiceImpl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RegistryHandler extends ChannelInboundHandlerAdapter {

    private Map<String, Object> registryMap = new HashMap<>();

    public RegistryHandler() {
        // 注册服务
        registryMap.put(SayHelloService.class.getName(), new SayHelloServiceImpl());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务端收到请求：" + msg);
        if (msg instanceof InvokerProtocol) {
            InvokerProtocol protocol = ((InvokerProtocol) msg);
            Object object = registryMap.get(protocol.getClassName());
            Method method = object.getClass().getDeclaredMethod(protocol.getMethodName(), protocol.getParameterTypes());
            Object result = method.invoke(object, protocol.getValues());
            ctx.write(result);
            ctx.flush();
            ctx.close();
        }
    }
}
