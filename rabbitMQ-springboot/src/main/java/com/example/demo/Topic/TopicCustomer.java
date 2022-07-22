package com.example.demo.Topic;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicCustomer {
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    key = {"user.save.*"},
                    exchange = @Exchange(name = "topics",type = "topic")
            )
    })
    public void receive1(String message) {
        System.out.println("message1 = " + message);
    }
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    key = {"user.*.findOne"},
                    exchange = @Exchange(type = "topic",name = "topics")
            )
    })
    public void receive2(String message) {
        System.out.println("message2 = " + message);
    }
}
