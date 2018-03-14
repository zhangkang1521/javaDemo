package org.zk.cgb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhangkang on 2018/3/14.
 */
@XStreamAlias("BEDC")
public class BEDC {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
