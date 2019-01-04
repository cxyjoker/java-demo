package com.cxy.kafka.consumer;

import com.cxy.service.AIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @ClassName KafkaConsumer
 * @Author chenxiangyu-001
 * @Date 2018/12/19
 * @Version 1.0
 */
@Component
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

//    private final AIService aiService;
//
//    @Autowired
//    public KafkaConsumer(AIService aiService) {
//        this.aiService = aiService;
//    }
//
//    /**
//     * 接受kafka信息
//     * @param message 信息
//     */
//    @KafkaListener(topics={"${kafka.ai.topic.listen}"})
//    public void receive(@Payload String message){
//        logger.info("KafkaMessageConsumer 接收到消息："+message);
//        aiService.dealResult(message);
//    }


}
