package com.gonghui.estest.service;

import com.gonghui.estest.config.RabbitDeadLetterConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RabbitListener(queues = RabbitDeadLetterConfig.REDIRECT_QUEUE)
@Component
@Slf4j
public class RedirectQueueConsumer {

    /**
     * 重定向队列和死信队列形参一致Integer number
     * @param number
     */
    @RabbitHandler
    public void fromDeadLetter(String number){
        Integer num = Integer.parseInt(number);
        System.out.println("RedirectQueueConsumer : "+ number);
        // 对应的操作
        int i = num / 1;
    }

}