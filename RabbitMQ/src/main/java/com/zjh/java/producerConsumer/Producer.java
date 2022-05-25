package com.zjh.java.producerConsumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;
import java.util.concurrent.TimeoutException;

//生产者
public class Producer {
    public static final String QUEUE_NAME = "hello2";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("124.220.200.142");
        factory.setUsername("guest");
        factory.setPassword("guest");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            //通道绑定对应消息队列
            //参数1:  队列名称 如果队列不存在自动创建
            //参数2:  用来定义队列特性是否要持久化 true 持久化队列   false 不持久化
            //参数3:  exclusive 是否独占队列  true 独占队列   false  不独占
            //参数4:  autoDelete: 是否在消费完成后自动删除队列  true 自动删除  false 不自动删除
            //消费者线程关闭之后才会进行删除
            //参数5:  额外附加参数
            channel.queueDeclare(QUEUE_NAME,true,false, true,null);
            String message = "Hello World";
            //发布消息
            //参数1: 交换机名称 参数2:队列名称  参数3:传递消息额外设置  参数4:消息的具体内容
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            System.out.println("消息发送完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
