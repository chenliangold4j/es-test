package com.gonghui.estest.service;

import com.gonghui.estest.config.RabbitDeadLetterConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DeadLetterConsumer {

    /*@RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitDeadLetterConfig.DEAD_LETTER_QUEUE, durable = "true"),
            exchange = @Exchange(value = RabbitDeadLetterConfig.DEAD_LETTER_EXCHANGE, type = ExchangeTypes.DIRECT),
            key = RabbitDeadLetterConfig.DEAD_LETTER_TEST_ROUTING_KEY)
    )*/

    /**
     * 手动Ack如果处理方式不对会发生一些问题。
     * 1.没有及时ack，或者程序出现bug，所有的消息将被存在unacked中，消耗内存
     * 如果忘记了ack，那么后果很严重。当Consumer退出时，Message会重新分发。然后RabbitMQ会占用越来越多的内存，由于 RabbitMQ会长时间运行，因此这个“内存泄漏”是致命的。
     * 2.如果使用BasicNack，将消费失败的消息重新塞进队列的头部，则会造成死循环。
     * （解决basicNack造成的消息循环循环消费的办法是为队列设置“回退队列”，设置回退队列和阀值，如设置队列为q1，阀值为2，则在rollback两次后将消息转入q1）
     *
     * 综上，手动ack需要注意的是：
     * 1.在消费者端一定要进行ack，或者是nack，可以放在try方法块的finally中执行
     * 2.可以对消费者的异常状态进行捕捉，根据异常类型选择ack，或者nack抛弃消息，nack再次尝试
     * 3.对于nack的再次尝试，是进入到队列头的，如果一直是失败的状态，将会造成阻塞。所以最好是专门投递到“死信队列”，具体分析。
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = RabbitDeadLetterConfig.DEAD_LETTER_QUEUE)
    public void process(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        System.out.println("DeadLetterConsumer :" + msg);
        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        //事实上手动提交的时候，basicNack的最后一个参数requeue = true时，消息会被无限次的放入消费队列重新消费，直至回送ACK。
//        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
    }
}
