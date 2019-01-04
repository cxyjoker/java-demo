package com.cxy.ml.common.utils;

/**
 * @ClassName MathUtils
 * @Author chenxiangyu-001
 * @Date 2018/10/11
 * @Version 1.0
 */
public class MathUtils {

    /**
     * 以base为底求对数
     * log_base(value)
     * @param value
     * @param base
     * @return
     */
    public static double log(double value, double base) {
        return Math.log(value) / Math.log(base);
    }

}
