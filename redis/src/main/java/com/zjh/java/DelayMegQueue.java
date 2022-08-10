package com.zjh.java;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;
import sun.nio.cs.ext.MS874;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class DelayMegQueue {
    private Jedis jedis;
    private String queue;

    public DelayMegQueue(Jedis jedis, String queue) {
        this.jedis = jedis;
        this.queue = queue;
    }
    public void queue(Object data){
        RedisMessage msg = new RedisMessage();
        msg.setId(UUID.randomUUID().toString());
        msg.setData(data);

        try {
            String s = new ObjectMapper().writeValueAsString(msg);
            System.out.println("msg publish" +new Date());
            jedis.zadd(queue,System.currentTimeMillis()+5000,s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void loop(){
        while(!Thread.interrupted()){
            Set<String> zrange = jedis.zrangeByScore(queue, 0, System.currentTimeMillis(), 0, 1);
            if (zrange.isEmpty()){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
                continue;
            }
            String next = zrange.iterator().next();
            if (jedis.zrem(queue,next)>0){
                try {
                    RedisMessage msg = new ObjectMapper().readValue(next, RedisMessage.class);
                    System.out.println("receive msg" + msg);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
