package com.cxy.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName RabbitReceiver
 * @Author chenxiangyu-001
 * @Date 2018/12/27
 * @Version 1.0
 */
@Component
//@RabbitListener(queues = "hello.queue1",containerFactory = "containerFactory")
//@RabbitListener(queues = "hello.queue1")
public class RabbitReceiver {

    private static final Logger logger = LoggerFactory.getLogger(RabbitReceiver.class);

//    @RabbitHandler
    public void process(String msg) {
        logger.info("111接收到来自hello.queue1队列的消息：" + msg);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        return msg.toUpperCase();
    }

//    @RabbitListener(queues = "hello.queue1")
//    public void processMessage2(String msg) {
//        logger.info("222接收到来自hello.queue1队列的消息：" + msg);
//        try {
//            Thread.sleep(130);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
