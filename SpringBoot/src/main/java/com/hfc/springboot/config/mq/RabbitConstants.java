package com.hfc.springboot.config.mq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by hfc on 2020/11/2.
 */
@Component
public class RabbitConstants {

    @Value("${spring.rabbitmq.listener.concurrency}")
    public int concurrency;
    @Value("${spring.rabbitmq.listener.max-concurrency}")
    public int maxConcurrency;
    @Value("${spring.rabbitmq.listener.prefetch}")
    public int prefetch;

    @Value("${mq.queue.simpleModeName}")
    public String simpleModeQueueName;
    @Value("${mq.queue.workModeName}")
    public String workModeQueueName;
    @Value("${mq.exchange.pubSubName}")
    public String pubSubExchangeName;
    @Value("${mq.exchange.routingName}")
    public String routingExchangeName;
    @Value("${mq.exchange.topicName}")
    public String topicExchangeName;

}
