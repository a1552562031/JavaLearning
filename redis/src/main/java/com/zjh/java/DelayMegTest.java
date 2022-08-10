package com.zjh.java;

public class DelayMegTest {
    public static void main(String[] args) {
        Redis redis = new Redis();
        redis.execute(jedis -> {
            DelayMegQueue queue = new DelayMegQueue(jedis, "zjh-delay-queue");
            Thread producer = new Thread(){
                @Override
                public void run() {
                    for (int i = 0; i < 5; i++) {
                        queue.queue("www.baidu.com>>>>"+i);
                    }
                }
            };
            Thread consumer = new Thread(){
                @Override
                public void run() {
                    queue.loop();
                }
            };
            producer.start();
            consumer.start();
            try {
                Thread.sleep(7000);
                consumer.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
