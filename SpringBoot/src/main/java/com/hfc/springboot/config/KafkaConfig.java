package com.hfc.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by hfc on 2022/5/8.
 */
@Component
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    public String bootstrapServers;
    @Value("${spring.kafka.template.default-topic}")
    public String topic;

    @Value("${kafka.producer.feature.on}")
    public boolean producerOn;
    @Value("${spring.kafka.producer.acks}")
    public String producerAcks;
    @Value("${spring.kafka.producer.retries}")
    public int producerRetries;
    @Value("${spring.kafka.producer.batch-size}")
    public int producerBatchSize;
    @Value("${spring.kafka.producer.properties.linger.ms}")
    public int producerLingerMs;
    @Value("${spring.kafka.producer.buffer-memory}")
    public long producerBufferMemory;

    @Value("${kafka.consumer.feature.on}")
    public boolean consumerOn;
    @Value("${spring.kafka.consumer.group-id}")
    public String consumerGroup;
    @Value("${spring.kafka.consumer.enable-auto-commit}")
    public boolean consumerAutoCommit;
    @Value("${spring.kafka.consumer.auto-commit-interval}")
    public int consumerCommitInterval;
    @Value("${spring.kafka.consumer.auto-offset-reset}")
    public String consumerOffsetReset;

}
