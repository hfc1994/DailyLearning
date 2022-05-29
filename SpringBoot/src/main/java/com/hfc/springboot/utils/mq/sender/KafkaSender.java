package com.hfc.springboot.utils.mq.sender;

import com.hfc.springboot.config.KafkaConfig;
import org.apache.kafka.clients.producer.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by hfc on 2022/5/8.
 */
@ConditionalOnProperty(value = "kafka.producer.feature.on")
@Component
public class KafkaSender {

    private static final Logger logger = LogManager.getLogger(KafkaSender.class);

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private KafkaConfig kafkaConfig;
    private Producer<String, String> producer;

    @PostConstruct
    public void init() {
        this.producer = new KafkaProducer<>(kafkaProperties.buildProducerProperties());
        logger.info("kafkaSender finish init");
    }

    public void send(String msg) {
        try {
//            this.producer.send(new ProducerRecord<>(kafkaConfig.topic,"msg", msg));
            this.producer.send(new ProducerRecord<>(kafkaConfig.topic, "msg", msg), (recordMetadata, e) -> {
                if (e != null) {
                    System.out.println("some exception occur, msg: " + e.getMessage());
                } else {
                    System.out.printf("kafka send callback, topic: %s, offset: %d%n", recordMetadata.topic(), recordMetadata.offset());
                }
            });
            logger.info("send a msg");
        } catch (Exception e) {
            logger.error("exception occured");
            throw new RuntimeException(e);
        }
    }

    public void closeProducer() {
        try {
            this.producer.close();
        } catch (Exception e) {
            logger.error("fail to close producer");
        }
    }

}
