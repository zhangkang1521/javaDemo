package org.zk;

import redis.clients.jedis.Jedis;

public interface CallWithJedis {
	void call(Jedis jedis);
}
