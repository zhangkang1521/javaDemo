package org.zk.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 实现2个整数相加解码器，有状态，不可共用
 * @author zhangkang
 * @date 2024/12/7 21:34
 */
@Slf4j
public class IntegerAddDecoder extends ReplayingDecoder<IntegerAddDecoder.PHASE> {

    enum PHASE {
        PHASE_1, PHASE_2
    }

    private int first;

    private int second;

    public IntegerAddDecoder() {
        // 初始化为阶段1
        super(PHASE.PHASE_1);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()) {
            case PHASE_1:
                first = in.readInt();
                log.info("读到first:{}", first);
                // checkpoint作用：设置checkpoint，类似于mark标记；切换到下一个状态
                checkpoint(PHASE.PHASE_2);
                break;
            case PHASE_2:
                second = in.readInt();
                log.info("读到second:{}", second);
                int sum = first + second;
                log.info("编码结果:{}", sum);
                out.add(sum);
                checkpoint(PHASE.PHASE_1);
            default:
                break;
        }
    }


}
