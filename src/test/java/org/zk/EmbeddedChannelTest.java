package org.zk;

import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.zk.netty.SimpleServerHandler;

/**
 * @author zhangkang
 * @date 2024/11/19 16:36
 */
@Slf4j
public class EmbeddedChannelTest {


    @Test
    public void readAndWrite() {
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.writeInbound("hello");
        System.out.println((String)channel.readInbound());

        channel.writeOutbound("world");
        System.out.println((String)channel.readOutbound());
    }

    @Test
    public void mockInbound() {
        EmbeddedChannel channel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast("test", new InboundReadHandle());
            }
        });

        // 设置ByteBuff内存分配器
        channel.config().setAllocator(UnpooledByteBufAllocator.DEFAULT);

        // 写入
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(new byte[]{1});
        // 模拟入站消息
        channel.writeInbound(buf);

        ByteBuf buf2 = (ByteBuf)channel.readOutbound();
        log.info("outboundBuff {}", buf2.alloc());
    }

    @Slf4j
    static class InboundReadHandle extends ChannelInboundHandlerAdapter {
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // ctx.pipeline().remove(this);
          if (msg instanceof ByteBuf) {
                ByteBuf buf = (ByteBuf) msg;
                // 默认是池化直接内存
                log.info("allocate:{}", buf.alloc());
                while (buf.isReadable()) {
                    System.out.println(buf.readByte());
                }

                ByteBuf writeBuf = ctx.alloc().buffer();
                writeBuf.writeByte(4);
                ctx.writeAndFlush(writeBuf);
            }
        }

    }

    @Test
    public void mockOutbound() {
        EmbeddedChannel channel = new EmbeddedChannel(new OutboundHandle());
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeBytes(new byte[]{1,2,3});
        // 模拟出站消息
        channel.writeOutbound(buf);

        // 出站处理器处理之后读出
        ByteBuf buf2 = (ByteBuf)channel.readOutbound();
        while (buf2.isReadable()) {
            System.out.println(buf2.readByte());
        }
    }

    static class OutboundHandle extends ChannelOutboundHandlerAdapter {
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            System.out.println("OutboundHandle write");
            if (msg instanceof ByteBuf) {
                ByteBuf buf = (ByteBuf) msg;
                buf.writeByte(4);
            }
            super.write(ctx, msg, promise);
        }
    }

    @Test
    public void testInboundHandlerAllMethod() {
        // handlerAdded -> channelRegistered -> channelActive
        EmbeddedChannel channel = new EmbeddedChannel(new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
//                ch.pipeline().addLast("allMethod", new InboundHandleAllMethod());
                ch.pipeline().addLast(new InboundHandleAllMethod());
            }
        });
        // channelRead -> channelReadComplete
        channel.writeInbound("hello");
        // channelRead -> channelReadComplete
        channel.writeInbound("hello");
        // inActive -> unRegistered -> handlerRemoved
        channel.close();
    }

    @Slf4j
    static class InboundHandleAllMethod implements ChannelInboundHandler {

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            log.info("handlerAdded");
        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            log.info("channelRegistered");
            ctx.fireChannelRegistered();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            log.info("channelActive");
            // fire...
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            log.info("channelInactive");
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            log.info("channelUnregistered");
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            log.info("handlerRemoved");
        }


        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("channelRead");
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            log.info("channelReadComplete");
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {

        }





        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        }
    }

    static class InboundHandle1 extends ChannelInboundHandlerAdapter {
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof String) {
                msg = msg + "-1";;
                ctx.fireChannelRead(msg);

                ctx.writeAndFlush("echo1 " + msg);
            }
        }
    }

    static class InboundHandle2 extends ChannelInboundHandlerAdapter {
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof String) {
                msg = msg + "-2";;

                ctx.fireChannelRead(msg);

                ctx.writeAndFlush("echo2 " + msg);
            }
        }
    }

    static class InboundHandle3 extends ChannelInboundHandlerAdapter {
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof String) {
                msg = msg + "-3";;

                ctx.fireChannelRead(msg);

                ctx.writeAndFlush("echo3 " + msg);
            }
        }
    }

    static class OutboundHandle1 extends ChannelOutboundHandlerAdapter {

    }
}
