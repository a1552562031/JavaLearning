package com.zjh.rate_limiter.aspect;

import com.zjh.rate_limiter.annotation.RateLimiter;
import com.zjh.rate_limiter.exception.RateLimiterException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Aspect
@Component
public class RateLimiterAspect {

    private static final Logger logger = LoggerFactory.getLogger(RateLimiterAspect.class);

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    RedisScript<Long> redisScript;

    @Before("@annotation(rateLimiter)")
    public void before(JoinPoint jp, RateLimiter rateLimiter){
        int time = rateLimiter.time();
        int count = rateLimiter.count();
        String combineKey = getCombineKey(rateLimiter,jp);
        Long number = redisTemplate.execute(redisScript, Collections.singletonList(combineKey), time, count);
        if (number == null || number.intValue() >count){
            logger.info("当前接口已达到最大限流次数");
            try {
                throw new RateLimiterException("访问过于频繁，请稍后访问");
            } catch (RateLimiterException e) {
                throw new RuntimeException(e);
            }
        }
        logger.info("一个时间窗内请求次数：{},当前请求次数：{},缓存的key为：{}",count,number,combineKey);
    }

    private String getCombineKey(RateLimiter rateLimiter, JoinPoint jp) {
        return null;
    }
}
