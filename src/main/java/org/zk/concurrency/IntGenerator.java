package org.zk.concurrency;

/**
 * Created by zhangkang on 2019/5/19.
 */
public abstract class IntGenerator {

    private volatile boolean canceled = false;
    public abstract int next();

    public void cancel() {
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
