package com.gonghui.estest.service;

import com.gonghui.estest.config.RabbitMQConfig2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitMQConfig2.ITEM_QUEUE)
@Slf4j
public class MyListener2 {

    @RabbitHandler
    public void refundConsumer(String str) {
        System.out.println("---------------------------");
        System.out.println(str);
    }
}