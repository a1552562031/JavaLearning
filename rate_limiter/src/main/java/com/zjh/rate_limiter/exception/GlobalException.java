package com.zjh.rate_limiter.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(RateLimiterException.class)
    public Map<String,Object> rateLimitException(RateLimiterException e){
        HashMap<String, Object> map = new HashMap<>();
        map.put("status",500);
        map.put("message",e.getMessage());
        return map;
    }
}
