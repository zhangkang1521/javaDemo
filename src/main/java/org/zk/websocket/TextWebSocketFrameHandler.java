package org.zk.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangkang
 * @date 2025/1/5 19:42
 */
@Slf4j
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    ExecutorService executors = Executors.newSingleThreadExecutor();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelActive();
        log.info("websocket client active {}", ctx.channel().remoteAddress());
        executors.submit(new Task(ctx.channel()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) msg).text();
            log.info("收到客户端消息：{}", text);
            TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame("echo:" + text);
            ctx.channel().writeAndFlush(textWebSocketFrame);
        }
    }

    static class Task implements Runnable {

        private Channel channel;

        public Task(Channel channel) {
            this.channel = channel;
        }

        @SneakyThrows
        @Override
        public void run() {
            // 实际场景可以根据具体条件，给客户端发送消息，不用客户端轮询
            for (int i = 0; i < 10000; i++) {
                TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame("hello" +i);
                channel.writeAndFlush(textWebSocketFrame);
                Thread.sleep(1000);
            }
        }
    }
}
