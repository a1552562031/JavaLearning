package com.zjh.java.Fanout;

import com.rabbitmq.client.*;
import com.zjh.java.Utils.RabbitMqUtils;

import java.io.IOException;

public class Customer1 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("logs","fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName,"logs","");
        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1：" + new String(body));
            }
        });
    }
}
