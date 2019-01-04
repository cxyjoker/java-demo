package com.cxy.common.handler;

import com.cxy.common.ErrorCode;
import com.cxy.common.exception.BaseException;
import com.cxy.common.model.ResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局捕获异常，捕获@requestMapping的方法中抛出的异常
 * 捕获异常父类Exception，如需捕获自定义类，需在toResponse方法中加判断
 * @ClassName TGlobalExceptionHandler
 * @Author chenxiangyu-001
 * @Date 2018/8/20
 * @Version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public GlobalExceptionHandler() {}

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultModel toResponse(Exception exception) {
        ResultModel<Object> resultModel = new ResultModel<>();
        resultModel.setModel(null);
        resultModel.setSuccess(Boolean.FALSE);
        resultModel.setCode(ErrorCode.SYSTEM_ERROR.getCode());
        resultModel.setMessage(exception.getMessage());
        LOGGER.error("全局捕获异常GlobalExceptionHandler："+exception.getMessage(),exception);
        return resultModel;
    }

    /**
     * 拦截捕捉自定义异常 BaseException.class
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BaseException.class)
    public ResultModel myExceptionHandler(BaseException exception){
        ResultModel<Object> resultModel = new ResultModel<>();
        resultModel.setModel(null);
        resultModel.setSuccess(Boolean.FALSE);
        resultModel.setCode(exception.getErrorCode().getCode());
        resultModel.setMessage(exception.getErrorMessage());
        LOGGER.error("全局捕获异常GlobalExceptionHandler："+exception.getMessage(),exception);
        return resultModel;
    }
}
