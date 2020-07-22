package com.gonghui.estest.controller;

import com.gonghui.estest.config.RabbitDeadLetterConfig;
import com.gonghui.estest.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMsgController {

    //注入RabbitMQ的模板
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 测试
     */
    @GetMapping("/sendmsg")
    public String sendMsg(@RequestParam String msg, @RequestParam String key){

        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.setMandatory(true);
        /**
         * 发送消息
         * 参数一：交换机名称
         * 参数二：路由key: item.springboot-rabbitmq,符合路由item.#规则即可
         * 参数三：发送的消息
         */
        rabbitTemplate.convertAndSend(RabbitMQConfig.ITEM_TOPIC_EXCHANGE ,key ,msg);
        //返回消息
        return "发送消息成功！";
    }


    @GetMapping("/sendmsg2")
    public String sendMsg2(@RequestParam String msg, @RequestParam String key){
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.convertAndSend(RabbitDeadLetterConfig.DEAD_LETTER_EXCHANGE ,RabbitDeadLetterConfig.DEAD_LETTER_TEST_ROUTING_KEY ,msg);
        //返回消息
        return "发送消息成功！";
    }



    final RabbitTemplate.ConfirmCallback confirmCallback= new RabbitTemplate.ConfirmCallback() {

        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.out.println("correlationData: " + correlationData);
            System.out.println("ack: " + ack);
            if(!ack){
                System.out.println("异常处理....");
            }
        }

    };

    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {

        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            System.out.println("return exchange: " + exchange + ", routingKey: "
                    + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
        }
    };



}