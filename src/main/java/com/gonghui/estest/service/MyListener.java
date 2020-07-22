package com.gonghui.estest.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyListener {

//    @RabbitListener(queues = "item_queue")
//    public void msg(String msg){
//        System.out.println("消费者消费消息了："+msg);
//        //TODO 这里可以做异步的工作
//    }

//    @RabbitListener(queues = "item_queue")
//    public void msg2(String msg){
//        System.out.println("消费者消费消息了："+msg);
//        //TODO 这里可以做异步的工作
//    }

    private static int i = 1;

    /**
     * 如设置成manual手动确认，一定要对消息做出应答，否则rabbit认为当前队列没有消费完成，将不再继续向该队列发送消息。
     * <p>
     * channel.basicAck(long,boolean); 确认收到消息，消息将被队列移除，false只确认当前consumer一个消息收到，true确认所有consumer获得的消息。
     * <p>
     * channel.basicNack(long,boolean,boolean); 确认否定消息，第一个boolean表示一个consumer还是所有，第二个boolean表示requeue是否重新回到队列，true重新入队。
     * <p>
     * channel.basicReject(long,boolean); 拒绝消息，requeue=false 表示不再重新入队，如果配置了死信队列则进入死信队列。
     * <p>
     * 当消息回滚到消息队列时，这条消息不会回到队列尾部，而是仍是在队列头部，这时消费者会又接收到这条消息，如果想消息进入队尾，须确认消息后再次发送消息。
     *
     *
     * channel.basicReject(deliveryTag, true);
     *         basic.reject方法拒绝deliveryTag对应的消息，第二个参数是否requeue，true则重新入队列，否则丢弃或者进入死信队列。
     *
     * 该方法reject后，该消费者还是会消费到该条被reject的消息。
     *
     * channel.basicNack(deliveryTag, false, true);
     *         basic.nack方法为不确认deliveryTag对应的消息，第二个参数是否应用于多消息，第三个参数是否requeue，与basic.reject区别就是同时支持多个消息，可以nack该消费者先前接收未ack的所有消息。nack后的消息也会被自己消费到。
     *
     * channel.basicRecover(true);
     *         basic.recover是否恢复消息到队列，参数是是否requeue，true则重新入队列，并且尽可能的将之前recover的消息投递给其他消费者消费，而不是自己再次消费。false则消息会重新被投递给自己。
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = "item_queue")
    public void process(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        // 确认收到消息，false只确认当前consumer一个消息收到，true确认所有consumer获得的消息
        if (i % 3 == 1) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("basicAck: " + new String(message.getBody()));
        } else if (i % 3 == 2) {
            // 拒绝消息，requeue=false 表示不再重新入队，如果配置了死信队列则进入死信队列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("basicReject: " + new String(message.getBody()));
        } else {
            // requeue为是否重新回到队列，true重新入队
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            System.out.println("basicNack: " + new String(message.getBody()));
        }
        i++;

    }
}