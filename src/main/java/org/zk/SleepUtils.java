package org.zk;

import java.util.concurrent.TimeUnit;

public class SleepUtils {

    public static void second(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
