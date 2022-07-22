package com.zjh.java.WorkQuene;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.zjh.java.Utils.RabbitMqUtils;


public class Task01 {
    public static final String QUEUE_NAME = "work";
    public static void main(String[] args) throws Exception{
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("",QUEUE_NAME,MessageProperties.PERSISTENT_TEXT_PLAIN,(i+"消息发送次数").getBytes());
        }
        RabbitMqUtils.closeConnectionAndChanel(channel,connection);
    }
}
