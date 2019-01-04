package com.cxy.controller;

import com.alibaba.fastjson.JSON;
import com.cxy.dao.UserRepo;
import com.cxy.entity.User;
import com.cxy.common.model.ResultModel;
import com.cxy.rabbitmq.RabbitSender;
import com.cxy.service.AIService;
import com.cxy.service.KafkaService;
import com.cxy.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName TestController
 * @Author chenxiangyu-001
 * @Date 2018/10/30
 * @Version 1.0
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/test")
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(TestController.class);

    private final UserRepo userRepo;

//    private final KafkaService kafkaService;

    private final AIService aiService;

    private final RabbitSender rabbitSender;

    @Autowired
    public TestController(AIService aiService, UserRepo userRepo, RabbitSender rabbitSender) {
        this.aiService = aiService;
//        this.kafkaService = kafkaService;
        this.userRepo = userRepo;
        this.rabbitSender = rabbitSender;
    }

    @RequestMapping("/hello")
    public String hello(@RequestParam String name){
        return "hello"+name;
    }

    @RequestMapping("/getUserByName")
    @ResponseBody
    public User getUserByName(String name){
        User u = userRepo.findUserByName(name);
        System.out.println(u==null?"无对应值，name="+name:u.toString());
        return u;
    }

    @RequestMapping("/sendMsg")
    @ResponseBody
    public String sendMsg(String msg){
//        kafkaService.sendMsg(msg);
        rabbitSender.send(msg);
        return "success";
    }

    @RequestMapping(value = "/identification",method = RequestMethod.POST)
    public ResultModel identification(@RequestParam String image,
                                      @RequestParam String clientId){
        ResultModel<JSON> resultModel = new ResultModel<>();
        if(StringUtils.isBlank(image) || StringUtils.isBlank(clientId)){
            logger.info("identification方法参数错误{}",resultModel.getMessage());
            return resultModel;
        }
        resultModel = aiService.aiService(image);
        logger.info("OcrController.identification ={}",resultModel.getModel());
        return resultModel;
    }
}
