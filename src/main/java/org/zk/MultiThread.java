package org.zk;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class MultiThread {
    public static void main(String[] args) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        // 一个简单的java程序也是多线程在运行
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(threadInfo.getThreadId() + "," + threadInfo.getThreadName());
        }
    }
}
