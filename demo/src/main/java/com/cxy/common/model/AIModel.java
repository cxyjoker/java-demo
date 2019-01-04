package com.cxy.common.model;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.CountDownLatch;

/**
 * 识别模型
 * @ClassName AIModel
 * @Author chenxiangyu-001
 * @Date 2018/11/30
 * @Version 1.0
 */
public class AIModel {

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 计数器，用于控制访问线程
     */
    private CountDownLatch countDownLatch;

    /**
     * base64格式的图片
     */
    private String imageBase64;

    /**
     * 错误码
     */
    private String errorCode;

    private JSON resultMsg;

    /**
     * 调用的ai模型的标识
     */
    private String aiService;

    public AIModel(){}

    public AIModel(String id, CountDownLatch countDownLatch, String imageBase64){
        this.countDownLatch = countDownLatch;
        this.imageBase64 = imageBase64;
        this.id = id;
    }

    public AIModel(String id, String imageBase64){
        this.imageBase64 = imageBase64;
        this.id = id;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JSON getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(JSON resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getAiService() {
        return aiService;
    }

    public void setAiService(String aiService) {
        this.aiService = aiService;
    }
}
