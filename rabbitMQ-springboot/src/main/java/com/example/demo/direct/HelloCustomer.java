package com.example.demo.direct;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queuesToDeclare = @Queue(value = "hello",declare = "true",autoDelete = "true"))
public class HelloCustomer {
    @RabbitHandler
    public void receive1(String message){
        System.out.println("message:" +message);
    }
}
