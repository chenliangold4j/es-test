package com.gonghui.estest.service;

import com.gonghui.estest.config.RabbitDeadLetterConfig;
import com.rabbitmq.client.Channel;
import com.sun.org.apache.xpath.internal.operations.String;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Slf4j
public class RedirectQueueConsumer {

    /**
     * 重定向队列和死信队列形参一致Integer number
     */
    @RabbitListener(queues = RabbitDeadLetterConfig.REDIRECT_QUEUE)
    public void fromDeadLetter(Message message, Channel channel) throws IOException {
        java.lang.String msg = new java.lang.String(message.getBody());
        System.out.println("RedirectQueueConsumer : "+ msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}