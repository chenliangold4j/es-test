package com.gonghui.estest.service;

import com.gonghui.estest.config.RabbitDeadLetterConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitDeadLetterConfig.DEAD_LETTER_QUEUE)
public class DeadLetterConsumer {

    /*@RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitDeadLetterConfig.DEAD_LETTER_QUEUE, durable = "true"),
            exchange = @Exchange(value = RabbitDeadLetterConfig.DEAD_LETTER_EXCHANGE, type = ExchangeTypes.DIRECT),
            key = RabbitDeadLetterConfig.DEAD_LETTER_TEST_ROUTING_KEY)
    )*/
    @RabbitHandler
    public void testDeadLetterQueueAndThrowsException(@Payload String number) {
        Integer num = Integer.parseInt(number);
        System.out.println("DeadLetterConsumer :" + num + "/0 ");
        int i = num / 0;
    }
}
