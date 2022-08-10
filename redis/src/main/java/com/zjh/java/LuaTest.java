package com.zjh.java;

import redis.clients.jedis.params.SetParams;

import java.util.Arrays;
import java.util.UUID;

public class LuaTest {
    public static void main(String[] args) {
        Redis redis = new Redis();
        for (int i = 0; i < 2; i++) {
            redis.execute(jedis -> {
                String value = UUID.randomUUID().toString();
                String k1 =jedis.set("k1",value,new SetParams().nx().ex(5));
                if (k1 != null && "OK".equals(k1)){
                    jedis.set("site","www.baidu.com");
                    String site = jedis.get("site");
                    System.out.println(site);
                    jedis.evalsha("b8059ba43af6ffe8bed3db65bac35d452f8115d8",
                            Arrays.asList("k1"),
                            Arrays.asList(value));
                }else {
                    System.out.println("没拿到锁");
                }
            });
        }
    }
}
