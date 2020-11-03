package com.hfc.springboot.utils.mq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by hfc on 2020/11/2.
 */
@Component
public class RabbitReceiver {

    @RabbitHandler
    @RabbitListener(queues = {"${mq.queue.simpleModeName}"},
                    containerFactory = "singleListenerContainer")
    public void simpleProcess(String msg) {
        System.out.println("Thread id " + Thread.currentThread().getId() + ", receive = " + msg);
    }

    @RabbitHandler
    @RabbitListener(queues = {"${mq.queue.workModeName}"},
            containerFactory = "multiListenerContainer")
    public void workProcess(String msg) {
        System.out.println("Thread id " + Thread.currentThread().getId() + ", receive = " + msg);
    }

    @RabbitHandler
    @RabbitListener(queues = {"fanoutQueue1"})
    public void fanoutProcess1(String msg) {
        System.out.println("Thread id " + Thread.currentThread().getId() + ", fanoutQueue1 receive = " + msg);
    }

    @RabbitHandler
    @RabbitListener(queues = {"fanoutQueue2", "fanoutQueue3"})
    public void fanoutProcess2And3(String msg) {
        System.out.println("Thread id " + Thread.currentThread().getId() + ", fanoutQueue2 or fanoutQueue3 receive = " + msg);
    }

    @RabbitHandler
    @RabbitListener(queues = {"topicQueue1"})
    public void topicProcess1(String msg) {
        System.out.println("Thread id " + Thread.currentThread().getId() + ", topicQueue1 receive = " + msg);
    }

    @RabbitHandler
    @RabbitListener(queues = {"topicQueue2"})
    public void topicProcess2(String msg) {
        System.out.println("Thread id " + Thread.currentThread().getId() + ", topicQueue2 receive = " + msg);
    }
}
