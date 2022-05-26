package com.zjh.java.RoutingDirect;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zjh.java.Utils.RabbitMqUtils;

import java.io.IOException;

public class Provider {
//    在Direct模型下：队列与交换机的绑定，不能是任意绑定了，
//    而是要指定一个RoutingKey（路由key）
//    消息的发送方在 向 Exchange发送消息时，
//    也必须指定消息的 RoutingKey。
//
//    Exchange不再把消息交给每一个绑定的队列，
//    而是根据消息的Routing Key进行判断，
//    只有队列的Routingkey与消息的 Routing key完全一致，
//    才会接收到消息
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "logs_direct";
        channel.exchangeDeclare(exchangeName,"direct");
        //根据生产者发送消息的routingkey进行绑定发送到不同消费者
        String routingKey = "error";
        channel.basicPublish(exchangeName,routingKey,null,("这是direct模型发布的基于route key: ["+routingKey+"] 发送的消息").getBytes());
        RabbitMqUtils.closeConnectionAndChanel(channel,connection);
    }
}
