package com.zjh.java.WorkQuene;

import com.rabbitmq.client.*;
import com.zjh.java.Utils.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Work02 {
    public static final String QUEUE_NAME = "work";
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(1);
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        channel.basicConsume(QUEUE_NAME,false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者-2  " + new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
