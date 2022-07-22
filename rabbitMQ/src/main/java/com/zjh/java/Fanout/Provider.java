package com.zjh.java.Fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zjh.java.Utils.RabbitMqUtils;

import java.io.IOException;

public class Provider {
    //fanout 扇出 也称为广播
//    在广播模式下，消息发送流程是这样的：
//
//    可以有多个消费者
//    每个消费者有自己的queue（队列）
//    每个队列都要绑定到Exchange（交换机）
//    生产者发送的消息，只能发送到交换机，
//    交换机来决定要发给哪个队列，生产者无法决定。
//    交换机把消息发送给绑定过的所有队列
//    队列的消费者都能拿到消息。实现一条消息被多个消费者消费

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("logs","fanout");
        channel.basicPublish("logs","",null,"fanout type message".getBytes());
        RabbitMqUtils.closeConnectionAndChanel(channel,connection);
    }
}
