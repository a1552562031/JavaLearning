package com.zjh.java;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public class RateLimiter {
    private Jedis jedis;
    public RateLimiter(Jedis jedis){
        this.jedis=jedis;
    }

    public boolean isAllowed(String user,String action,int period,int maxCount){
        String key = user +"-" +action;
        long nowTime = System.currentTimeMillis();
        Pipeline pipeline = new Pipeline();
        pipeline.multi();
        pipeline.zadd(key,nowTime,String.valueOf(nowTime));
        pipeline.zremrangeByScore(key,0,nowTime-period*1000);
        Response<Long> response = pipeline.zcard(key);
        pipeline.expire(key,period+1);
        pipeline.exec();
        pipeline.close();
        return response.get() <= maxCount;
    }

    public static void main(String[] args) {
        Redis redis = new Redis();
        redis.execute(jedis -> {
            RateLimiter rateLimiter = new RateLimiter(jedis);
            for (int i = 0; i < 20; i++) {
                  System.out.println(rateLimiter.isAllowed("javaboy", "publish", 5, 3));
            }
        });
    }
}
