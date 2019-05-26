package org.zk.concurrency;

/**
 * Created by zhangkang on 2019/5/26.
 */
public class SerialNumberGenerator {
    private static volatile int serialNumber = 0;

    public static synchronized int nextSerialNumber() {
        return serialNumber++;
    }
}
