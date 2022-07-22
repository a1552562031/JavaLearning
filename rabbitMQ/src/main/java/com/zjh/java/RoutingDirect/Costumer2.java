package com.zjh.java.RoutingDirect;

import com.rabbitmq.client.*;
import com.zjh.java.Utils.RabbitMqUtils;

import java.io.IOException;

public class Costumer2 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "logs_direct";
        channel.exchangeDeclare(exchangeName,"direct");
        String queue = channel.queueDeclare().getQueue();
        //根据生产者发送消息的routingkey进行绑定发送到不同消费者
        channel.queueBind(queue,exchangeName,"error");
        channel.queueBind(queue,exchangeName,"info");
        channel.queueBind(queue,exchangeName,"warning");
        channel.basicConsume(queue,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2" + new String(body));
            }
        });

    }
}
