package com.hfc.springboot.utils.mq.sender;

import com.hfc.springboot.config.KafkaConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * Created by hfc on 2022/5/8.
 */
@ConditionalOnProperty(value = "kafka.producer.feature.on")
@Component
public class KafkaSender {

    private static final Logger logger = LogManager.getLogger(KafkaSender.class);

    @Autowired
    private KafkaConfig kafkaConfig;
    private Producer<String, String> producer;

    public Properties getProp() {
        Properties p = new Properties();
        p.put("bootstrap.servers", kafkaConfig.bootstrapServers);
        p.put("acks", kafkaConfig.producerAcks);
        p.put("retries", kafkaConfig.producerRetries);
        p.put("batch.size", kafkaConfig.producerBatchSize);
        p.put("linger.ms", kafkaConfig.producerLingerMs);
        p.put("buffer.memory", kafkaConfig.producerBufferMemory);

        // compression.type：数据压缩格式，有snappy、gzip和lz4，snappy算法比较均衡，gzip会消耗更高的cpu，但压缩比更高
        // key和value的序列化
        p.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        p.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return p;
    }

    @PostConstruct
    public void init() {
        this.producer = new KafkaProducer<>(this.getProp());
        logger.info("kafkaSender finish init");
    }

    public void send(String msg) {
        try {
            this.producer.send(new ProducerRecord<>(kafkaConfig.topic,"msg", msg));
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
