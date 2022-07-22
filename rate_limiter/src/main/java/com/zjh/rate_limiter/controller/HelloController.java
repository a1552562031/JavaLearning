package com.zjh.rate_limiter.controller;

import com.zjh.rate_limiter.annotation.RateLimiter;
import com.zjh.rate_limiter.enums.LimitType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    @RateLimiter(time=10, count=3, limitType= LimitType.IP)
    public String hello(){
        return "Hello";
    }
}
