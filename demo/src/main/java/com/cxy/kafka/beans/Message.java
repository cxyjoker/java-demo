package com.cxy.kafka.beans;

//import lombok.Data;

import java.util.Date;

/**
 * @ClassName Message
 * @Author chenxiangyu-001
 * @Date 2018/12/19
 * @Version 1.0
 */
//@Data
public class Message {
    private Long id;    //id

    private String msg; //消息

    private Date sendTime;  //时间戳
}
