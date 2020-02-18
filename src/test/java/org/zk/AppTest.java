package org.zk;


import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void test1() {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.127.128", 6379);
        Jedis jedis = pool.getResource();
//        for (int i = 0; i < 100000; i++)
            jedis.set("c", "xx");
    }
}
