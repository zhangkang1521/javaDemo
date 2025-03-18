package org.zk;

import org.junit.Test;

/**
 * @author zhangkang
 * @date 2025/3/17 13:46
 */
public class ThreadLocalTest {

    private static final ThreadLocal<String> USER_CONTEXT = new ThreadLocal<>();

    private static final ThreadLocal<String> SESSION_CONTEXT = new ThreadLocal<>();

    @Test
    public void test() {
        // 每个线程有一个map, key是threadLocal
        USER_CONTEXT.set("zk");
        USER_CONTEXT.set("zk2");
        SESSION_CONTEXT.set("123456");
        SESSION_CONTEXT.get();
    }
}
