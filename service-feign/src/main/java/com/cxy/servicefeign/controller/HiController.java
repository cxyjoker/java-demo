package com.cxy.servicefeign.controller;

import com.cxy.servicefeign.service.SchedualServiceHi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HiController
 * @Author chenxiangyu-001
 * @Date 2019/1/3
 * @Version 1.0
 */
@RestController
public class HiController {

    private final SchedualServiceHi schedualServiceHi;

    @Autowired
    public HiController(SchedualServiceHi schedualServiceHi) {
        this.schedualServiceHi = schedualServiceHi;
    }

    @GetMapping(value = "/hi")
    public String sayHi(@RequestParam String name) {
        return schedualServiceHi.sayHiFromClientOne( name );
    }
}
