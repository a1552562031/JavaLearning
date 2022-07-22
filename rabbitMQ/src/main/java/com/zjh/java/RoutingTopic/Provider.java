package com.zjh.java.RoutingTopic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zjh.java.Utils.RabbitMqUtils;

import java.io.IOException;

public class Provider {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("topics","topic");
        String routekey = "save2.user.delete2";
        channel.basicPublish("topics",routekey,null,("这里是topic动态路由模型,routekey: ["+routekey+"]").getBytes());
        RabbitMqUtils.closeConnectionAndChanel(channel,connection);
    }
}
