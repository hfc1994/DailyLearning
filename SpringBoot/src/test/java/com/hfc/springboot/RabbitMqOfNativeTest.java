package com.hfc.springboot;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

/**
 * Created by hfc on 2020/11/2.
 */
@RunWith(SpringRunner.class)
// 就可以不启动现有的Spring容器，容器里的mq相关会影响以下的测试
// @SpringBootTest
public class RabbitMqOfNativeTest {

    private static String HOST;
    private static int PORT;
    private static String USERNAME;
    private static String PASSWORD;
    private static String VIRTUALHOST;

    private static String simpleModeQueueName;
    private static String workModeQueueName;
    private static String pubSubExchangeName;
    private static String routingExchangeName;
    private static String topicExchangeName;

    private static ConnectionFactory connFactory = null;
    private static Connection producer_conn = null;
    private static Channel producer_channel = null;

    @Before
    public void doConnect() throws IOException, TimeoutException {
        Properties p = new Properties();
        p.load(RabbitMqOfSpringTest.class.getClassLoader().getResourceAsStream("../classes/application.properties"));
        HOST = p.getProperty("spring.rabbitmq.host");
        PORT = Integer.parseInt(p.getProperty("spring.rabbitmq.port"));
        USERNAME = p.getProperty("spring.rabbitmq.username");
        PASSWORD = p.getProperty("spring.rabbitmq.password");
        VIRTUALHOST = p.getProperty("spring.rabbitmq.virtual-host");
        simpleModeQueueName = p.getProperty("mq.queue.simpleModeName");
        workModeQueueName = p.getProperty("mq.queue.workModeName");
        pubSubExchangeName = p.getProperty("mq.exchange.pubSubName");
        routingExchangeName = p.getProperty("mq.exchange.routingName");
        topicExchangeName = p.getProperty("mq.exchange.topicName");


        connFactory = new ConnectionFactory();
        connFactory.setHost(HOST);
        connFactory.setPort(PORT);
        connFactory.setUsername(USERNAME);
        connFactory.setPassword(PASSWORD);
        connFactory.setVirtualHost(VIRTUALHOST);

        producer_conn = connFactory.newConnection();
        producer_channel = producer_conn.createChannel();
    }

    @After
    public void doRelease() throws IOException, TimeoutException {
        producer_channel.close();
        producer_conn.close();
    }

    /**
     * 其它写法使用基本和TestRabbitMqOfSpring类似
     */
    @Test
    public void testSimpleMode() throws IOException, TimeoutException, InterruptedException {
        // 统一由生产者创建队列
        // 声明队列是幂等的，只有不存在时才创建
        producer_channel.queueDeclare(simpleModeQueueName, false, false, false, null);

        // simple mode - 会使用默认的exchange
        String msg = "hello world to simple mode";
        // 使用了默认的exchange
        producer_channel.basicPublish("", simpleModeQueueName, null, msg.getBytes());
        System.out.println("--- simple mode sent msg = " + msg);

        CountDownLatch cdt = new CountDownLatch(1);

        // 创建消费者的连接和通道
        // 不能和TestRabbitMqOfSpring里面一样使用try-wirh-resource的写法，因为它是缓存连接池
        Connection conn = connFactory.newConnection();
        Channel channel = conn.createChannel();

        DeliverCallback deliverCallback = (consumerTag, deliver) -> {
            String repMsg = new String(deliver.getBody(), StandardCharsets.UTF_8);
            System.out.println("--- received msg = " + repMsg);
            // 手动确认消息已接受并处理
            channel.basicAck(deliver.getEnvelope().getDeliveryTag(), false);
            cdt.countDown();
        };

        // false表示手动ack模式，只有被ack的消息才会被RabbitMQ给清除掉，否则会超时重传
        boolean autoAck = false;
        channel.basicConsume(simpleModeQueueName, autoAck, deliverCallback, cancelCallback -> {});

        cdt.await();
        channel.close();
        conn.close();
    }
}
