package com.zjh.java;

import redis.clients.jedis.params.SetParams;

public class LockTest {
    public static void main(String[] args) {
        Redis redis = new Redis();
        redis.execute(jedis -> {
            String set = jedis.set("k1", "v1", new SetParams().nx().ex(5));
            //没人占用
            if (set != null && "OK".equals(set)){
                //给锁加一个过期时间 防止应用在运行过程中抛出异常导致锁无法释放
                jedis.expire("k1",5);
                jedis.set("name","zjh");
                String name = jedis.get("name");
                System.out.println(name);
                //执行完毕释放锁
                jedis.del("k1");
            }
            else{

            }
        });
    }
}
