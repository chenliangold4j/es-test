package com.gonghui.estest.service;

import com.gonghui.estest.config.RabbitDeadLetterConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component

public class DeadLetterConsumer {

    /*@RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitDeadLetterConfig.DEAD_LETTER_QUEUE, durable = "true"),
            exchange = @Exchange(value = RabbitDeadLetterConfig.DEAD_LETTER_EXCHANGE, type = ExchangeTypes.DIRECT),
            key = RabbitDeadLetterConfig.DEAD_LETTER_TEST_ROUTING_KEY)
    )*/
    @RabbitListener(queues = RabbitDeadLetterConfig.DEAD_LETTER_QUEUE)
    public void process(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        System.out.println("DeadLetterConsumer :" + msg);
//        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
    }

}
