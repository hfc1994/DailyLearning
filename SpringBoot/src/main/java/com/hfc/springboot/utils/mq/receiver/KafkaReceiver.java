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
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * Created by hfc on 2022/5/11.
 */
@ConditionalOnProperty(value = "kafka.consumer.feature.on")
@Component
public class KafkaReceiver {

    private static final Logger logger = LogManager.getLogger(KafkaReceiver.class);

    @Autowired
    private KafkaConfig kafkaConfig;
    private Consumer<String, String> consumer;

    public Properties getProp() {
        Properties p = new Properties();
        p.put("bootstrap.servers", kafkaConfig.bootstrapServers);
        p.put("group.id", kafkaConfig.consumerGroup);
        p.put("session.timeout.ms", kafkaConfig.consumerTimeout);
        p.put("enable.auto.commit", kafkaConfig.consumerAutoCommit);
        p.put("auto.commit.interval.ms", kafkaConfig.consumerCommitInterval);
        p.put("auto.offset.reset", kafkaConfig.consumerOffsetReset);

        // compression.type：数据压缩格式，有snappy、gzip和lz4，snappy算法比较均衡，gzip会消耗更高的cpu，但压缩比更高
        // key和value的序列化
        p.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        p.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return p;
    }

    @PostConstruct
    public void init() {
        this.consumer = new KafkaConsumer<>(this.getProp());
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

    public void closeConsumer() {
        try {
            this.consumer.close();
        } catch (Exception e) {
            logger.error("fail to close consumer");
        }
    }

}
