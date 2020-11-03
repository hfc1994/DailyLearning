package com.hfc.springboot.config.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.impl.AMQImpl;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ChannelListener;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hfc on 2020/11/2.
 */
@Configuration
public class RabbitConfig {

    @Autowired
    private RabbitConstants rabbitConstants;

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    /**
     * 单一消费者配置
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        connectionFactory.addChannelListener(new ChannelListener() {
            @Override
            public void onCreate(Channel channel, boolean b) {
                System.out.println("--- channel create ---");
            }

            @Override
            public void onShutDown(ShutdownSignalException signal) {
                System.out.println("--- channel shutdown ---");
                AMQImpl.Connection.Close close = (AMQImpl.Connection.Close) signal.getReason();
                System.out.println("reply text: " + close.getReplyText());
                // 可根据isHardError判断是channel断开还是connection断开
                if (signal.isHardError()) {
                    System.out.println("--- channel shutdown with hard error ---");
                }
            }
        });
        connectionFactory.addConnectionListener(new ConnectionListener() {
            @Override
            public void onCreate(Connection connection) {
                System.out.println("--- connection create ---");
            }

            @Override
            public void onClose(Connection connection) {
                System.out.println("--- connection close ---");
            }

            @Override
            public void onShutDown(ShutdownSignalException signal) {
                System.out.println("--- connection shutdown ---");
                AMQImpl.Connection.Close close = (AMQImpl.Connection.Close) signal.getReason();
                System.out.println("reply text: " + close.getReplyText());

            }
        });
        factory.setConnectionFactory(connectionFactory);
        // FIXME: 2020/11/3 加了之后会导致消息解析成一串ASCII，原因未知
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        factory.setTxSize(1);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    /**
     * 多消费者配置
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory, connectionFactory);
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(rabbitConstants.concurrency);
        factory.setMaxConcurrentConsumers(rabbitConstants.maxConcurrency);
        factory.setPrefetchCount(rabbitConstants.prefetch);
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        return factory;
    }

    // ---- simple mode
    @Bean
    public Queue simpleQueue() {
        return new Queue(rabbitConstants.simpleModeQueueName,
                false,
                false,
                false);
    }

    // ---- work mode
    @Bean
    public Queue workQueue() {
        return new Queue(rabbitConstants.workModeQueueName,
                false,
                false,
                false);
    }

    // ---- pub/sub mode
    @Bean
    public FanoutExchange pubSubFanoutExchange() {
        return new FanoutExchange(rabbitConstants.pubSubExchangeName,
                            false,
                            false);
    }

    // FIXME: 2020/11/3 本计划使用自动生成的name，但是如果用自动生成的name
    // FIXME: 2020/11/3 那么接收器那里不知道怎么绑定了，所以暂时写死
    @Bean
    public Queue fanoutQueue1() {
//        String queueName = UUIDNamingStrategy.DEFAULT.generateName();
        return new Queue("fanoutQueue1",
                        false,
                        false,
                        true);
    }

    @Bean
    public Queue fanoutQueue2() {
        return new Queue("fanoutQueue2",
                false,
                false,
                true);
    }

    @Bean
    public Queue fanoutQueue3() {
        return new Queue("fanoutQueue3",
                false,
                false,
                true);
    }

    @Bean
    public Binding fanoutBinding1(Queue fanoutQueue1, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    @Bean
    public Binding fanoutBinding2(Queue fanoutQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }

    @Bean
    public Binding fanoutBinding3(Queue fanoutQueue3, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue3).to(fanoutExchange);
    }

    // --- topic mode
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(rabbitConstants.topicExchangeName,
                            false,
                            false);
    }

    @Bean
    public Queue topicQueue1() {
        return new Queue("topicQueue1",
                false,
                false,
                true);
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue("topicQueue2",
                false,
                false,
                true);
    }

    @Bean
    public Binding topicBinding1(Queue topicQueue1, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueue1).to(topicExchange).with("#.error");
    }

    @Bean
    public Binding topicBinding2(Queue topicQueue2, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueue2).to(topicExchange).with("*.error");
    }

    @Bean
    public Binding topicBinding3(Queue topicQueue2, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueue2).to(topicExchange).with("*.info");
    }
}
