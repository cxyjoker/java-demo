package com.cxy.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cxy.common.ErrorCode;
import com.cxy.common.model.AIModel;
import com.cxy.common.model.ResultModel;
import com.cxy.rabbitmq.RabbitSender;
import com.cxy.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName AIService
 * @Author chenxiangyu-001
 * @Date 2018/12/20
 * @Version 1.0
 */
@Service("aiService")
public class AIService {

    private static final Logger logger = LoggerFactory.getLogger(AIService.class);

    public volatile static ConcurrentHashMap<String, AIModel> aiMap = new ConcurrentHashMap<>();

//    private final KafkaService kafkaService;

    @Value("${ai.rabbitmq.response.queue}")
    private String responseQueue;

    private final RabbitSender rabbitSender;

    @Autowired
    public AIService(RabbitSender rabbitSender) {
        this.rabbitSender = rabbitSender;
    }

    public ResultModel<JSON> aiService(String imgBase64){
        ResultModel<JSON> resultModel = new ResultModel<>();
        String id = getOcrId();
        logger.info("生成id={}", id);
        long start = System.currentTimeMillis();
        AIModel aiModel = new AIModel(id,new CountDownLatch(1), imgBase64);
        aiMap.put(id,aiModel);
        rabbitSender.send(bulidMessage(id,imgBase64));
        boolean notTimeout = false;
        //等待模型返回
        try {
            notTimeout = aiModel.getCountDownLatch().await(3000, TimeUnit.MILLISECONDS);
            resultModel.setSuccessModel(aiModel.getResultMsg());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }finally {
            aiMap.remove(id);
        }
        if(!notTimeout){
            //超时
            resultModel.setMessage("识别服务超时");
        }
        logger.info("处理id={}，总耗时{}毫秒",id,System.currentTimeMillis()-start);
        return resultModel;
    }

    /**
     * 生成识别图片id
     */
    private String getOcrId() {
        String times = String.valueOf(System.currentTimeMillis());
        return "Ocr" + times + new Random().nextInt(1000);
    }

    /**
     * 拼接发送字符串
     * @param id id
     * @param data 图片数据
     * @return
     *  {
     *      id:识别id,
     *      data:识别数据,
     *      time:时间戳,
     *      resultTopic:结果返回主题
     *  }
     */
    private String bulidMessage(String id,String data){
        JSONObject json = new JSONObject();
        json.put("ocrId",id);
        json.put("imageBase64",data);
        json.put("time",System.currentTimeMillis());
        json.put("responseQueue",responseQueue);
        JSONArray array = new JSONArray();
        array.add(json);
        return array.toString();
    }

}
