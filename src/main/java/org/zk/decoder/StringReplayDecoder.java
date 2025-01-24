package org.zk.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 防止字符串半包，粘包，生产环境不建议使用
 * @author zhangkang
 * @date 2024/12/7 21:34
 */
@Slf4j
public class StringReplayDecoder extends ReplayingDecoder<StringReplayDecoder.PHASE> {

    enum PHASE {
        PHASE_1, PHASE_2
    }

    private int length;

    private byte[] inBytes;

    public StringReplayDecoder() {
        // 初始化为阶段1
        super(PHASE.PHASE_1);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()) {
            case PHASE_1:
                length = in.readInt();
                log.info("读到length:{}", length);
                inBytes = new byte[length];
                // checkpoint作用：设置checkpoint，类似于mark标记；切换到下一个状态
                checkpoint(PHASE.PHASE_2);
                break;
            case PHASE_2:
                in.readBytes(inBytes, 0, length);
                out.add(new String(inBytes, "UTF-8"));
                checkpoint(PHASE.PHASE_1);
                break;
            default:
                break;
        }
    }


}
