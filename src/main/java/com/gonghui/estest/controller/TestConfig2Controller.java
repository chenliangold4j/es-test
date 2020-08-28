package com.gonghui.estest.controller;

import com.gonghui.estest.config.RabbitMQConfig2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestConfig2Controller {

    @Autowired
    RabbitTemplate rabbitTemplate;


    @GetMapping("/testConfig2")
    public String send() {
        rabbitTemplate.convertAndSend(RabbitMQConfig2.ITEM_TOPIC_EXCHANGE, RabbitMQConfig2.ITEM_KEY, "年后");

        return "ok";
    }

}
