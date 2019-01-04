package com.cxy.common;

import com.cxy.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * ErrorCode:错误码
 *
 * @ClassName ErrorCode
 * @Author chenxiangyu-001
 * @Date 2018/12/1
 * @Version 1.0
 */
public enum ErrorCode {
    /**
     * 成功状态
     */
    SUCCESS("10000", "success"),

    /**
     * 参数异常
     */
    PARAMETERS_ERROR("10001", "Parameter error"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR("10002", "System Error"),

    /**
     * 重复调用接口
     */
    REPEATED_CALL_ERROR("10003", "Repeated Call error"),

    /**
     * 超时
     */
    TIMEOUT("10004","timeout"),

    /**
     * 识别服务调用异常
     */
    OCR_SERVICE_ERROR("10005","ocr service call error"),

    /**
     * 数据处理异常
     */
    OCR_DATA_PROCESS_ERROR("10101","ocr service data process error"),

    /**
     * Base64内容错误
     */
    OCR_IMG_BASE64_ERROR("10102","image format is not base64"),

    /**
     * 非图像数据
     */
    OCR_NOT_IMG_ERROR("10103","image format is not picture"),

    /**
     * 不含火车票图片
     */
    OCR_NOT_TICKET_IMG_ERROR("10104","not ticket image error"),

    /**
     * ai服务系统异常
     */
    OCR_SYSTEM_ERROR("10105","ocr service system error");

    private String code;
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 根据code返回错误描述，未找到返回system_error
     * @param code
     * @return
     */
    public static String getMsgByCode(String code){
        for (ErrorCode errorCode : ErrorCode.values()) {
            if(StringUtils.equals(errorCode.getCode(),code)){
                return errorCode.getMessage();
            }
        }
        return ErrorCode.SYSTEM_ERROR.getMessage();
    }

    public static Map<Integer,String> ocrErrorCodeMap;

    static {
        ocrErrorCodeMap = new HashMap<>();
        ocrErrorCodeMap.put(0,ErrorCode.SUCCESS.code);
        ocrErrorCodeMap.put(1,ErrorCode.OCR_DATA_PROCESS_ERROR.code);
        ocrErrorCodeMap.put(2,ErrorCode.OCR_IMG_BASE64_ERROR.code);
        ocrErrorCodeMap.put(3,ErrorCode.OCR_NOT_IMG_ERROR.code);
        ocrErrorCodeMap.put(4,ErrorCode.OCR_NOT_TICKET_IMG_ERROR.code);
        ocrErrorCodeMap.put(5,ErrorCode.OCR_SYSTEM_ERROR.code);
    }
}
