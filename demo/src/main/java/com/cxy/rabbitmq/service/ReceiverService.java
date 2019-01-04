package com.cxy.rabbitmq.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cxy.common.ErrorCode;
import com.cxy.common.model.AIModel;
import com.cxy.service.AIService;
import com.cxy.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ReceiverService
 * @Author chenxiangyu-001
 * @Date 2018/12/28
 * @Version 1.0
 */
@Service
public class ReceiverService {

    private static final Logger logger = LoggerFactory.getLogger(ReceiverService.class);

    /**
     * 处理kafka返回结果
     * @param resultMsg 结果字符串，
     *                  格式：[{ocrId:ocrId,errorCode:errorCode,aiService:aiService,ocrResult:[{识别结果},{识别结果}]}]
     */
    public void dealResult(String resultMsg){
        List<Map> imgs = null;
        try {
            imgs = JSONObject.parseArray(resultMsg,Map.class);
        }catch (Exception e){
            logger.info("解析返回值错误",e);
            return;
        }
        for (Map obj : imgs) {
            String ocrId = (String) obj.get("ocrId");
            if(StringUtils.isBlank(ocrId)){
                logger.error("ai模型返回模型缺少ocrId，result={}",resultMsg);
            }
            //orcMap中的model，发生超时会移除，导致无法找到
            AIModel aiModel = null;
            if(AIService.aiMap != null){
                aiModel = AIService.aiMap.get(ocrId);
            }
            if(aiModel == null){
                logger.info("在aiModel中未找到对应id，返回超时");
                continue;
            }
            JSON result;
            if(obj.get("ocrResult") instanceof String){
                result = new JSONArray();
            }else{
                result = (JSON) obj.get("ocrResult");
            }
            aiModel.setAiService(String.valueOf(obj.get("aiService")));
            aiModel.setErrorCode(getSystemCode((Integer)obj.get("errorCode")));
            aiModel.setResultMsg(result);
            aiModel.getCountDownLatch().countDown();
        }
    }

    private String getSystemCode(Integer code){
        String result;
        if(code == null){
            logger.error("ai模型未返回错误码");
            result = ErrorCode.OCR_SERVICE_ERROR.getCode();
            return result;
        }
        result = ErrorCode.ocrErrorCodeMap.get(code);
        if(StringUtils.isBlank(result)){
            //未知错误码
            result = ErrorCode.SYSTEM_ERROR.getCode();
        }
        return result;
    }
}
