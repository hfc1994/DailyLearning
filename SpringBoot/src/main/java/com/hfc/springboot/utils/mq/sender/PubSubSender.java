package com.hfc.springboot.utils.mq.sender;

import com.hfc.springboot.config.mq.RabbitConstants;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by hfc on 2020/11/3.
 */
@Component
public class PubSubSender {

    @Autowired
    private RabbitConstants rabbitConstants;

    @Autowired
    private AmqpTemplate rabbitMQTemplate;

    public void send(String msg) {
        rabbitMQTemplate.convertAndSend(rabbitConstants.pubSubExchangeName, "", msg);
    }
}
