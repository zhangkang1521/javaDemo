package org.zk;


import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class RedisDataTest {

    String bigString = new String(new byte[64 * 1000 + 1]);
    Jedis jedis;

    @Before
    public void before() {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.127.132", 6379);
        jedis = pool.getResource();
    }

    @Test
    public void testString() {
        // int：小于等于long的最大值
        jedis.set("str1", String.valueOf(Long.MAX_VALUE));

        // embstr：大于long的最大值
        jedis.set("str2", "9223372036854775808"); // Long.MAX_VALUE + 1

        // embstr: 长度 < 40 字符串
        StringBuilder _39 = new StringBuilder();
        for (int i = 0; i < 39; i++) {
            _39.append("a");
        }
        jedis.set("str3", _39.toString());

        // raw: 长度 >= 40字符串
        StringBuilder _40 = new StringBuilder();
        for (int i = 0; i < 40; i++) {
            _40.append("a");
        }
        jedis.set("str4", _40.toString());
    }


    @Test
    public void testList() {
        // 查看编码：object encoding list1
        // ziplist
        jedis.lpush("list1", "a");
        // 元素个数大于512，使用linkedList
        for (int i = 0; i < 513; i++)
            jedis.lpush("list2", String.valueOf(i));
        // 元素大于64字节，使用linkedList
        jedis.lpush("list3", bigString);
    }

    @Test
    public void testHash() {
        // ziplist
        jedis.hset("hash1", "a", "aaa");
        // 元素个数大于512，使用hashTable
        for (int i = 0; i < 513; i++)
            jedis.hset("hash2", String.valueOf(i), String.valueOf(i));
        // 元素大于64字节，使用hashTable
        jedis.hset("hash3", "a", bigString);
    }

    @Test
    public void testSet() {
        // intset
        jedis.sadd("set1", "1");
        // 元素个数大于512，使用hashTable
        for (int i = 0; i < 513; i++)
            jedis.sadd("set2", String.valueOf(i));
        // 元素为字符串，使用HashTable
        jedis.sadd("set3", "a");
    }

    @Test
    public void testZSet() {
        // zipList
        jedis.zadd("zset1", 1, "a");
        // 元素个数大于128，使用skipList
        for (int i = 0; i < 129; i++)
            jedis.zadd("zset2", i, String.valueOf(i));
        // 元素值大于64kb，使用skipList
        jedis.zadd("zset3", 1, bigString);
    }
}
