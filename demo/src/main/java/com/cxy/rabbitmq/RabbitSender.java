package com.cxy.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName RabbitSender
 * @Author chenxiangyu-001
 * @Date 2018/12/27
 * @Version 1.0
 */
@Component
public class RabbitSender {

    private static final Logger logger = LoggerFactory.getLogger(RabbitSender.class);

    private final RabbitTemplate rabbitTemplate;

    @Value("${ai.rabbitmq.request.queue}")
    private String requestQueue;

    @Autowired
    public RabbitSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    //发送消息，不需要实现任何接口，供外部调用。
    public void send(String msg){
        logger.info("开始发送消息 : " + msg);
        rabbitTemplate.convertAndSend(requestQueue, msg);
    }

}
