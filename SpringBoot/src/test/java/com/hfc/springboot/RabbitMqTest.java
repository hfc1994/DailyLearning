package com.hfc.springboot;

import com.hfc.springboot.utils.mq.sender.PubSubSender;
import com.hfc.springboot.utils.mq.sender.SimpleSender;
import com.hfc.springboot.utils.mq.sender.TopicSender;
import com.hfc.springboot.utils.mq.sender.WorkSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by hfc on 2020/11/2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqTest {

    @Autowired
    private SimpleSender simpleSender;

    @Autowired
    private WorkSender workSender;

    @Autowired
    private PubSubSender pubSubSender;

    @Autowired
    private TopicSender topicSender;

    private int msgCount = 5;

    @Test
    public void testRabbitSimpleMode() {
        for (int i=0; i<msgCount; i++) {
            simpleSender.send("say hello to simple mode!");
        }
    }

    @Test
    public void testRabbitWorkMode() {
        for (int i=0; i<msgCount; i++) {
            workSender.send("say hello to work mode!");
        }
    }

    @Test
    public void testRabbitPubSubMode() {
        for (int i=0; i<msgCount; i++) {
            pubSubSender.send("say hello to pubsub mode!");
        }
    }

    @Test
    public void testRabbitTopicMode() {
        for (int i=0; i<msgCount; i++) {
            topicSender.send("kern.error!", "kern.error");
            topicSender.send("app.kern.error!", "app.kern.error");
            topicSender.send("app.info!", "app.info");
        }
    }
}
