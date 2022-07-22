package com.zjh.rate_limiter.annotation;

import com.zjh.rate_limiter.enums.LimitType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RateLimiter {
    //限流的key前缀
    String key() default "rate_limit";

    //限流时间窗
    int time() default 60;

    //限流次数
    int count() default 100;

    //限流类型 是按照ip还是默认
    LimitType limitType() default LimitType.DEFAULT;
}
