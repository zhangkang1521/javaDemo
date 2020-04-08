package org.zk;


import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class MemcachedTest {

    MemCachedClient memCachedClient;

    @Before
    public void before() {
        String poolName = "dataServer";
        String[] sessionServers = new String[] {"10.200.4.77:11211", "10.200.4.78:11211"};
        SockIOPool sessionPool = SockIOPool.getInstance(poolName);
        sessionPool.setServers(sessionServers);
        sessionPool.setFailover(true);
        sessionPool.setInitConn(10);
        sessionPool.setMinConn(5);
        sessionPool.setMaxConn(50);
        sessionPool.setMaintSleep(30);
        sessionPool.setNagle(false);
        sessionPool.setSocketTO(3000);
        sessionPool.setBufferSize(1024*1024*5);
        sessionPool.setAliveCheck(true);
        sessionPool.initialize();
        memCachedClient = new MemCachedClient(poolName);
    }

    @Test
    public void test1() {
        memCachedClient.set("a", "10"); // 注意设置成string类型，否则无法自增
        System.out.println(memCachedClient.incr("a"));
        System.out.println(memCachedClient.get("a"));
    }
}
