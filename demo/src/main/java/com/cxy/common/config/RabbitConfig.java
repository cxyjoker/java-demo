package com.cxy.common.config;

import com.cxy.rabbitmq.service.ReceiverService;
import com.cxy.service.AIService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.*;

/**
 * @ClassName RabbitConfig
 * @Author chenxiangyu-001
 * @Date 2018/12/27
 * @Version 1.0
 */
@Configuration
@ComponentScan({"com.cxy.rabbitmq.service"})
public class RabbitConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    private final ReceiverService receiverService;

    @Value("${ai.rabbitmq.host}")
    private String host;

    @Value("${ai.rabbitmq.port}")
    private int port;

    @Value("${ai.rabbitmq.username}")
    private String username;

    @Value("${ai.rabbitmq.password}")
    private String password;

    @Value("${ai.rabbitmq.request.exchange}")
    private String requestExchange;

    @Value("${ai.rabbitmq.request.routing.key}")
    private String requestRoutingKey;

    @Value("${ai.rabbitmq.request.queue}")
    private String requestQueue;

    @Value("${ai.rabbitmq.response.exchange}")
    private String responseExchange;

    @Value("${ai.rabbitmq.response.routing.key}")
    private String responseRoutingKey;

    @Value("${ai.rabbitmq.response.queue}")
    private String responseQueue;

    @Value("${ai.rabbitmq.request.durable}")
    private boolean requestDurable;

    @Value("${ai.rabbitmq.response.durable}")
    private boolean responseDurable;

    @Autowired
    public RabbitConfig(ReceiverService receiverService) {
        this.receiverService = receiverService;
    }

    @Bean
    public ConnectionFactory connectionFactory(){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public CachingConnectionFactory rabbitConnectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory());
        cachingConnectionFactory.setPublisherConfirms(true);
        return cachingConnectionFactory;
    }

//    定义rabbitmqTemplate
    @Bean
    public RabbitTemplate fixedReplyQRabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(rabbitConnectionFactory());
        return template;
    }


    //rabbitmqTemplate监听返回队列的消息
    @Bean
    public SimpleMessageListenerContainer replyListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(rabbitConnectionFactory());
        container.setQueueNames(responseQueue);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            String result = new String(message.getBody());
            logger.info("消费端接收到消息 : " + result);
            receiverService.dealResult(result);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        });
        ThreadFactory factory = new DefaultThreadFactory("rabbitmq-listener-thread");
        ExecutorService executorService=new ThreadPoolExecutor(20, 20, 10000, TimeUnit.MILLISECONDS,
                 new LinkedBlockingDeque<>(1024),factory);
        //指定线程池
        container.setTaskExecutor(executorService);
        container.setConcurrentConsumers(20);
        container.setPrefetchCount(5);
        return container;
    }

    //请求队列和交换器绑定
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(requestQueue())
                .to(topicExchange())
                .with(requestRoutingKey);
    }

//    返回队列和交换器绑定
    @Bean
    public Binding replyBinding() {
        return BindingBuilder.bind(replyQueue())
                .to(topicExchange2())
                .with(responseRoutingKey);
    }

    //请求队列
    @Bean
    public Queue requestQueue() {
        return new Queue(requestQueue,requestDurable);
    }

    //每个应用实例监听的返回队列
    @Bean
    public Queue replyQueue() {
        return new Queue(responseQueue,responseDurable);
    }

    @Bean
    DirectExchange topicExchange() {
        return new DirectExchange(requestExchange);
    }

    @Bean
    DirectExchange topicExchange2() {
        return new DirectExchange(responseExchange);
    }

    @Bean
    public RabbitAdmin admin() {
        return new RabbitAdmin(rabbitConnectionFactory());
    }

}
