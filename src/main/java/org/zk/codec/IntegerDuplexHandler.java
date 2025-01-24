package org.zk.codec;

import io.netty.channel.CombinedChannelDuplexHandler;
import org.zk.decoder.Byte2IntegerDecoder;
import org.zk.encoder.Integer2ByteEncoder;

/**
 * 编解码器（组合方式）
 * @author zhangkang
 * @date 2024/12/8 21:23
 */
public class IntegerDuplexHandler extends CombinedChannelDuplexHandler<Byte2IntegerDecoder, Integer2ByteEncoder> {

    public IntegerDuplexHandler() {
        super(new Byte2IntegerDecoder(), new Integer2ByteEncoder());
    }
}
