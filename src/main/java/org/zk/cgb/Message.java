package org.zk.cgb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhangkang on 2018/3/14.
 */
@XStreamAlias("message")
public class Message<T> {
    private CommHead commHead;
    private T body;

    public CommHead getCommHead() {
        return commHead;
    }

    public void setCommHead(CommHead commHead) {
        this.commHead = commHead;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
