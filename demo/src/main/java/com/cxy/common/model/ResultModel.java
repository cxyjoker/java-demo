package com.cxy.common.model;

import com.cxy.common.ErrorCode;
import com.cxy.utils.StringUtils;

import java.io.Serializable;

/**
 * ResultModel:结果返回模型
 * @ClassName ResultModel
 * @Author chenxiangyu-001
 * @Date 2018/12/1
 * @Version 1.0
 */
public class ResultModel<T> implements Serializable {

    /**
     * 实体对象
     */
    private T model;
    /**
     * 状态码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    private Boolean success;

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
        this.success = StringUtils.equals(code,ErrorCode.SUCCESS.getCode());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ResultModel() {
        setCodeAndMsg(ErrorCode.PARAMETERS_ERROR);
        this.success = Boolean.FALSE;
    }

    /**
     * 设置处理成功的model
     * @param model
     */
    public void setSuccessModel(T model){
        this.model = model;
        this.success = true;
        setCodeAndMsg(ErrorCode.SUCCESS);
    }

    public void setTimeout(T model){
        this.model = model;
        this.success = false;
        setCodeAndMsg(ErrorCode.TIMEOUT);
    }

    public void setCodeAndMsg(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.success = StringUtils.equals(code,ErrorCode.SUCCESS.getCode());
    }

    public void setCodeAndMsg(String code) {
        this.code = code;
        this.success = StringUtils.equals(code,ErrorCode.SUCCESS.getCode());
        this.message = ErrorCode.getMsgByCode(code);
    }
}
