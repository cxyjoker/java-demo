package com.cxy.common.exception;


import com.cxy.common.ErrorCode;

/**
 * @ClassName BaseException 自定义异常，用于全局捕获异常并返回前端
 * @Author chenxiangyu-001
 * @Date 2018/8/16
 * @Version 1.0
 */
public class BaseException extends RuntimeException{

    /**
     * 错误码
     */
    private ErrorCode errorCode;

    /**
     * 错误描述
     */
    private String errorMessage;

    public BaseException(ErrorCode errorCode, String errorMessage){
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BaseException(String errorMessage){
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
