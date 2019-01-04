package com.cxy.utils;

import org.slf4j.LoggerFactory;

/**
 * @ClassName StringUtils
 * @Author chenxiangyu-001
 * @Date 2018/12/5
 * @Version 1.0
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(StringUtils.class);

    /**
     * 字符串转换为int，字符串错误时返回默认值，且数值要大于等于0
     * @param str 数值字符串
     * @param defaultValue 默认值
     * @return
     */
    public static int parseToInt(String str, int defaultValue){
        int value;
        try {
            value = Integer.parseInt(str);
            if(value < 0){
                value = defaultValue;
            }
        }catch (NumberFormatException e){
            logger.error("线程池配置格式异常，配置值：{}",str,e);
            value = defaultValue;
        }
        return value;
    }

}
