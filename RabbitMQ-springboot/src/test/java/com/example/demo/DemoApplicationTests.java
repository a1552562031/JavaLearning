package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
class DemoApplicationTests {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void Topic (){
        rabbitTemplate.convertAndSend("topics" ,"user.save.findAll","这是topic user.save.findAll");
        rabbitTemplate.convertAndSend("topics" ,"user.save.findOne","这是路由直连 user.save.findOne");
        rabbitTemplate.convertAndSend("topics" ,"user.saveeee.findOne","这是路由直连 user.saveeee.findOne");
    }


    @Test
    public void Route (){
        rabbitTemplate.convertAndSend("directs" ,"error","这是路由直连 error信息");
        rabbitTemplate.convertAndSend("directs" ,"info","这是路由直连 info信息");
    }

    @Test
    public void Fanout(){
        rabbitTemplate.convertAndSend("logs" ,"","这是日志广播");
    }
    //work
    @Test
    public void teatWork(){
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work","work模型测试");

        }
    }
    //hello world -- direct
    @Test
    void testHello() {
        rabbitTemplate.convertAndSend("hello","first Message");
    }

}
