package org.zk.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.*;

/**
 * @author zhangkang
 * @date 2024/12/28 16:13
 */
@Slf4j
public class HttpEchoHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final AttributeKey<Boolean> KEEP_ALIVE = AttributeKey.newInstance("keepAlive");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (!request.decoderResult().isSuccess()) {
            log.info("decoder error");
            // 返回400
            return;
        }
        log.info("uri:{} method:{} remoteAddress:{}", request.uri(), request.method(), ctx.channel().remoteAddress());
        HttpHeaders headers = request.headers();
        Iterator<Map.Entry<String, String>> iterator = headers.entries().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
//            log.info("header {} : {}", entry.getKey(), entry.getValue());
            CharSequence connection = request.headers().get(HttpHeaderNames.CONNECTION);
            if (HttpHeaderValues.KEEP_ALIVE.contentEqualsIgnoreCase(connection)) {
                ctx.channel().attr(KEEP_ALIVE).set(true);
            }
        }
        paramFromUri(request);

        if ("POST".equals(request.method().name())) {
            dataFromPost(request);
        }

        ByteBuf byteBuf = Unpooled.copiedBuffer("{\n" +
                "\t\"hello\": \"world\"\n" +
                "}", CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, OK, byteBuf);
        Boolean keepAlive = ctx.channel().attr(KEEP_ALIVE).get();
        if (keepAlive == null || Boolean.FALSE.equals(keepAlive)) {
            // 关闭连接
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        }

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
        // 必须设置响应长度，否则客户端超时
        HttpUtil.setContentLength(response, response.content().readableBytes());
        ChannelFuture flushPromise = ctx.writeAndFlush(response);
        // 关闭连接
        if (keepAlive == null || Boolean.FALSE.equals(keepAlive)) {
            flushPromise.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private void paramFromUri(FullHttpRequest request) {
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
//        log.info("path:{}", decoder.path());
        Map<String, List<String>> params = decoder.parameters();
        for (Map.Entry<String, List<String>> entry : params.entrySet()) {
            String key = entry.getKey();
            // a=1&a=11 可以这样传参
            List<String> values = entry.getValue();
            log.info("param key:{} values:{}", key, values);
        }
    }

    private void dataFromPost(FullHttpRequest request) {
        String contentType = request.headers().get("Content-Type").trim();
        log.info("contentType:{}", contentType);
        if (contentType.contains("application/x-www-form-urlencoded")) {
            formBodyDecode(request);
        } else if (contentType.contains("multipart/form-data")) {
            formBodyDecode(request);
        } else if (contentType.contains("application/json")) {
            log.info("json:{}", request.content().toString(CharsetUtil.UTF_8));
        } else if (contentType.contains("text/plain")) {
            log.info("text:{}", request.content().toString(CharsetUtil.UTF_8));
        }
    }


    @SneakyThrows
    private void formBodyDecode(FullHttpRequest fullHttpRequest) {
        HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(fullHttpRequest);
        List<InterfaceHttpData> postData = decoder.getBodyHttpDatas();
        for (InterfaceHttpData data : postData) {
            if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                MixedAttribute attribute = (MixedAttribute) data;
                log.info("form param key:{} value:{}", attribute.getName(), attribute.getValue());
            } else if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload) {
                FileUpload fileUpload = (FileUpload) data;
                log.info("file upload {}", fileUpload.getName());
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        log.info("远程连接已经主动关闭， channel {}", ctx.channel());
//        cause.printStackTrace();
    }

}
