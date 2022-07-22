package com.zjh.java.WorkQuene;

import com.rabbitmq.client.*;
import com.zjh.java.Utils.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Work01 {
    public static final String QUEUE_NAME = "work";
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(1);//每次只能消费一条消息，避免中途宕机，后续消息丢失
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        channel.basicConsume(QUEUE_NAME,false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者-1  " + new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
