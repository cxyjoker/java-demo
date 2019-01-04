package com.cxy.common.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName AddressConfig
 * @Author chenxiangyu-001
 * @Date 2018/12/26
 * @Version 1.0
 */
@Component
public class AddressConfig implements ApplicationListener<WebServerInitializedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(AddressConfig.class);

    /**
     * 当前项目的端口
     */
    private int serverPort;

    public String getIp() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            logger.info("获取项目ip异常",e);
            throw new RuntimeException("获取项目ip异常");
        }
        if(address == null){
            throw new RuntimeException("获取项目ip异常");
        }
        return address.getHostAddress();
    }

    public int getServerPort(){
        return serverPort;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
    }
}
