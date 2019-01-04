package com.cxy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @ClassName KafkaService
 * @Author chenxiangyu-001
 * @Date 2018/12/19
 * @Version 1.0
 */
@Service
public class KafkaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    private final KafkaTemplate<String, String> kafkaTemplate;

//    @Value("${kafka.ai.topic.send}")
//    private String topic;

//    @Autowired
//    public KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }

//    /**
//     * 发送kafka信息
//     * @param message
//     */
//    public void sendMsg(String message){
//        logger.info("topic="+topic);
//
//        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
//        future.addCallback(success -> logger.info("KafkaMessageProducer 发送消息成功！"),
//                fail -> logger.error("KafkaMessageProducer 发送消息失败！"));
//    }
}
