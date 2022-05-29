package com.hfc.springboot.utils.mq.receiver;

import com.hfc.springboot.config.KafkaConfig;
import com.hfc.springboot.utils.Executor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;

/**
 * Created by hfc on 2022/5/11.
 */
@ConditionalOnProperty(value = "kafka.consumer.feature.on")
@Component
public class KafkaReceiver {

    private static final Logger logger = LogManager.getLogger(KafkaReceiver.class);

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private KafkaConfig kafkaConfig;
    private Consumer<String, String> consumer;

    @PostConstruct
    public void init() {
        this.consumer = new KafkaConsumer<>(kafkaProperties.buildConsumerProperties());
        this.consumer.subscribe(Collections.singletonList(kafkaConfig.topic));
        Executor.execute(() -> {
            System.out.println("--- wait to receive kafka msg ---");
            while (true) {
                ConsumerRecords<String, String> records = this.consumer.poll(Duration.ofMillis(100));
                if (records == null || records.isEmpty()) {
                    continue;
                }

                System.out.println("receive msg, count = " + records.count());
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset = %d, key = %s, value = %s%n",
                            record.offset(), record.key(), record.value());
                }
            }
        });
        logger.info("kafkaReceiver finish init");
    }

    /**
     * 不同的 groupId 可以与 consumer 实例一起接收信息
     */
    @KafkaListener(topics = "${spring.kafka.template.default-topic}",
            groupId = "${spring.kafka.consumer.group-id}-2")
    public void doConsumer(ConsumerRecord<String, String> record) {
        if (record == null) {
            return;
        }

        System.out.printf("record from KafkaListener, offset = %d, key = %s, value = %s%n",
                record.offset(), record.key(), record.value());
    }

    public void closeConsumer() {
        try {
            this.consumer.close();
        } catch (Exception e) {
            logger.error("fail to close consumer");
        }
    }

}
