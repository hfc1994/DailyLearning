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
    @Value("${kafka.topic}")
    public String topic;

    @Value("${kafka.producer.feature.on}")
    public boolean producerOn;
    @Value("${spring.kafka.producer.acks}")
    public String producerAcks;
    @Value("${spring.kafka.producer.retries}")
    public int producerRetries;
    @Value("${spring.kafka.producer.batch-size}")
    public int producerBatchSize;
    @Value("${kafka.linger.ms}")
    public int producerLingerMs;
    @Value("${spring.kafka.producer.buffer-memory}")
    public long producerBufferMemory;

    @Value("${kafka.consumer.feature.on}")
    public boolean consumerOn;
    @Value("${kafka.consumer.group-id}")
    public String consumerGroup;
    @Value("${kafka.session.timeout.ms}")
    public int consumerTimeout;
    @Value("${spring.kafka.consumer.enable-auto-commit}")
    public boolean consumerAutoCommit;
    @Value("${auto.commit.interval.ms}")
    public int consumerCommitInterval;
    @Value("${auto.offset.reset}")
    public String consumerOffsetReset;

}
