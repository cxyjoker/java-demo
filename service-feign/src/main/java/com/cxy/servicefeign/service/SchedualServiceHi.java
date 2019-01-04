package com.cxy.servicefeign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 通过@ FeignClient（“服务名”），来指定调用哪个服务
 * @ClassName SchedualServiceHi
 * @Author chenxiangyu-001
 * @Date 2019/1/3
 * @Version 1.0
 */
@FeignClient(value="service-hi",fallback = SchedualServiceHiHystric.class)
@Component
public interface SchedualServiceHi {

    @RequestMapping(value="/hi",method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value="name")String name);
}
