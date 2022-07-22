package com.zjh.java.ProducerConsumer;

import com.rabbitmq.client.*;

public class Consumer {
    public static final String QUEUE_NAME = "hello2";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("124.220.200.142");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        System.out.println("等待接收消息.........");
//推送的消息如何进行消费的接口回调
        DeliverCallback deliverCallback=(consumerTag,delivery)->{String message= new String(delivery.getBody());
        System.out.println(message);
};
//取消消费的一个回调接口 如在消费的时候队列被删除掉了
        CancelCallback cancelCallback=(consumerTag)->{System.out.println("消息消费被中断");
};
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
