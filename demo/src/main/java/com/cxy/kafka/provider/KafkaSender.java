package com.cxy.kafka.provider;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName KafkaSender
 * @Author chenxiangyu-001
 * @Date 2018/12/19
 * @Version 1.0
 */
//@Component
//public class KafkaSender {
//
//    @Autowired
//    private KafkaTemplate kafkaTemplate;
//
//    /**
//     * 发送消息到kafka,主题为test
//     */
//    public void sendTest(String msg){
//        kafkaTemplate.send("test",msg+" hello,kafka  "  + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
//    }
//}
