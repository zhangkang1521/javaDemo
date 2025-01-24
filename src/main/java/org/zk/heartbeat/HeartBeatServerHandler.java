package org.zk.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangkang
 * @date 2024/12/19 13:18
 */
@Slf4j
public class HeartBeatServerHandler extends IdleStateHandler {
    public HeartBeatServerHandler() {
        super(3, 0, 0);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        log.info("通道空闲自动关闭 state:{} isFirst:{}", evt.state(), evt.isFirst(), ctx.channel().remoteAddress());
        ctx.channel().close();
    }
}
