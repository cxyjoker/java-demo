package com.cxy.servicefeign.service;

import org.springframework.stereotype.Component;

/**
 * @ClassName SchedualServiceHiHystric
 * @Author chenxiangyu-001
 * @Date 2019/1/3
 * @Version 1.0
 */
@Component
public class SchedualServiceHiHystric implements SchedualServiceHi {

    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry"+name;
    }
}
