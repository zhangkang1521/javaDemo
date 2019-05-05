package net.mindview.util;

import java.util.concurrent.ThreadFactory;

/**
 * Created by zhangkang on 2019/5/2.
 */
public class DaemonThreadFactory implements ThreadFactory {
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}
