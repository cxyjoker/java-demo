package com.cxy.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 启动时修改配置文件内容
 * @ClassName KafkaConfig
 * @Author chenxiangyu-001
 * @Date 2018/12/26
 * @Version 1.0
 */
@Configuration
@ComponentScan({"com.cxy.common.config"})
public class PropertiesConfig implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesConfig.class);

    private final AddressConfig addressConfig;

    @Autowired
    public PropertiesConfig(AddressConfig addressConfig) {
        this.addressConfig = addressConfig;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String address = "ocr-service-"+addressConfig.getIp()+"-"+addressConfig.getServerPort();
        System.setProperty("ai.rabbitmq.response.queue", address);
        logger.info("#########  system config topic:{} ########", address);
    }

}
