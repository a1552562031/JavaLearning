package com.zjh.java;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Redis {
    private JedisPool pool;

    public Redis () {
        GenericObjectPoolConfig<Object> config = new GenericObjectPoolConfig<>();
        config.setMaxIdle(300);
        config.setMaxTotal(1000);
        config.setMaxWaitMillis(30000);
        config.setTestOnBorrow(true);
        pool = new JedisPool(config,"124.220.200.142",6379,30000,"123456");
    }
    public void execute(CallWithJedis callWithJedis){
        try (Jedis jedis = pool.getResource()){
            callWithJedis.call(jedis);
        }
    }
}
