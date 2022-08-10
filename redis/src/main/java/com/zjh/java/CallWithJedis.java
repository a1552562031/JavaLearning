package com.zjh.java;

import redis.clients.jedis.Jedis;

public interface CallWithJedis {
    void call(Jedis jedis);
}
