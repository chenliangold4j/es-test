package com.gonghui.estest.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig2 {
    //交换机名称
    public static final String ITEM_TOPIC_EXCHANGE = "item_direct_exchange";
    //队列名称
    public static final String ITEM_QUEUE = "item_direct_queue";

    public static final String ITEM_KEY = "itDirect";

    @Autowired
    RabbitTemplate rabbitTemplate;

    //声明交换机
    @Bean("itemDirectExchange")
    public Exchange directExchange() {
        //durable 持久化
        return ExchangeBuilder.topicExchange(ITEM_TOPIC_EXCHANGE).durable(true).build();
    }

    //声明队列
    @Bean("itemDirectQueue")
    public Queue directQueue() {
        return QueueBuilder.durable(ITEM_QUEUE).build();
    }

    //绑定队列和交换机
    @Bean
    public Binding itemQueueDirectExchange(@Qualifier("itemDirectQueue") Queue queue,
                                     @Qualifier("itemDirectExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("itDirect").noargs();
    }


}