package com.hfc.springboot;

import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.AMQImpl;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ChannelListener;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by hfc on 2020/10/31.
 *
 * 使用RabbitMQ的原生方法来收发消息
 */
@RunWith(SpringRunner.class)
// 就可以不启动现有的Spring容器，容器里的mq相关会影响以下的测试
// @SpringBootTest
public class TestRabbitMqOfSpring {

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

    private static CachingConnectionFactory connFactory = null;
    private static Connection producer_conn = null;
    private static Channel producer_channel = null;

    @BeforeClass
    public static void doInitAndConnect() throws IOException {
        Properties p = new Properties();
        p.load(TestRabbitMqOfSpring.class.getClassLoader().getResourceAsStream("../classes/application.properties"));
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


        connFactory = new CachingConnectionFactory();
        connFactory.setHost(HOST);
        connFactory.setPort(PORT);
        connFactory.setUsername(USERNAME);
        connFactory.setPassword(PASSWORD);
        connFactory.setVirtualHost(VIRTUALHOST);
        // 发送后需要确认
        connFactory.setPublisherConfirms(true);

        // 用于监听通道的创建和销毁
        connFactory.addChannelListener(new ChannelListener() {
            @Override
            public void onCreate(Channel channel, boolean b) {
                System.out.println("--- channel create ---");
            }

            @Override
            public void onShutDown(ShutdownSignalException signal) {
                System.out.println("--- channel shutdown ---");
                AMQImpl.Connection.Close close = (AMQImpl.Connection.Close) signal.getReason();
                System.out.println("reply text: " + close.getReplyText());
                // 可根据isHardError判断是channel断开还是connection断开
                if (signal.isHardError()) {
                    System.out.println("--- channel shutdown with hard error ---");
                }
            }
        });
        // 用于监听连接的创建和关闭
        connFactory.addConnectionListener(new ConnectionListener() {
            @Override
            public void onCreate(Connection connection) {
                System.out.println("--- connection create ---");
            }

            @Override
            public void onClose(Connection connection) {
                System.out.println("--- connection close ---");
            }

            @Override
            public void onShutDown(ShutdownSignalException signal) {
                System.out.println("--- connection shutdown ---");
                AMQImpl.Connection.Close close = (AMQImpl.Connection.Close) signal.getReason();
                System.out.println("reply text: " + close.getReplyText());

            }
        });
        // 监听自动重连的情况
        connFactory.setRecoveryListener(new RecoveryListener() {
            @Override
            public void handleRecovery(Recoverable recoverable) {
                System.out.println("--- recovery ---");
            }

            @Override
            public void handleRecoveryStarted(Recoverable recoverable) {
                System.out.println("--- recovery started ---");
            }
        });

        // 生产者的连接和通道
        producer_conn = connFactory.createConnection();
        producer_channel = producer_conn.createChannel(false);  // 是否开启事务
    }

    @AfterClass
    public static void doRelease() throws IOException, TimeoutException {
        producer_channel.close();
        producer_conn.close();
    }

    @Test
    public void testSimpleMode() throws IOException, InterruptedException {

        // 统一由生产者创建队列
        // 声明队列是幂等的，只有不存在时才创建
        // durable：表示是否需要持久化
        // exclusive：表示是否是独占队列，仅创建的那根连接可使用此队列
        // autoDelete：当所有消费者的连接断开后，是否自动删除队列
        producer_channel.queueDeclare(simpleModeQueueName, false, false, false, null);

        CountDownLatch cdt = new CountDownLatch(1);
        // simple mode - 会使用默认的exchange
        String msg = "hello world to simple mode";
        // 使用了默认的exchange
        producer_channel.basicPublish("", simpleModeQueueName, null, msg.getBytes());
        System.out.println("--- simple mode sent msg = " + msg);

        // 创建消费者的连接和通道
        // Spring里面用的是CachingConnectionFactory，连接和通道都被缓存起来了
        // 所以这里使用自动关闭资源的写法不会出错，不会导致接收不到数据
        Connection conn = connFactory.createConnection();
        Channel channel = conn.createChannel(false);

            // prefetchCount=2表示告诉RabbitMQ不要分发超过2个消息给client，除非client消费了消息（或则说未ack的少于2时）
            // 不设置时，RabbitMQ会公平的轮询client，然后不检查未ack的数据直接挨个分发消息（round-robin）
            int prefetchCount = 2;
            channel.basicQos(prefetchCount);

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
    }

    @Test
    public void testWorkMode() throws IOException, InterruptedException {

        // durable=true表示告知RabbitMQ这个队列是需要持久化的，数据会定时刷入硬盘，以免在server端崩溃时丢失消息
        // 设置了durable=true，发送消息时可以设置消息持久化方式，比如：channel.basicPublish("", "queue", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        // durable配置只接受在第一次声明队列（即server端还未有同名队列）时配置，已存在的队列不接受修改配置，否则会抛出一个错误
        boolean durable = false;
        producer_channel.queueDeclare(workModeQueueName, durable, false, false, null);

        // work mode - 基于simple mode增加消费者
        int msgCount = 5;
        int workerCount = 3;
        CountDownLatch cdt = new CountDownLatch(workerCount);

        for (int i=0; i<workerCount; i++) {
            String threadName = "work-thread-" + i;
            Thread t = new Thread(() -> {
                try (Connection conn = connFactory.createConnection();
                     Channel channel = conn.createChannel(false)) {

                    DeliverCallback deliverCallback = (consumerTag, deliver) -> {
                        String repMsg = new String(deliver.getBody(), StandardCharsets.UTF_8);
                        System.out.println("---" + threadName +" received msg = " + repMsg);
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        cdt.countDown();
                    };

                    // 不需要死循环，这里它能一直接受消息
                    channel.basicConsume(workModeQueueName, true, deliverCallback, cancelCallback -> {});
                } catch (TimeoutException | IOException e) {
                    e.printStackTrace();
                }
            });
            t.setDaemon(true);
            t.start();
        }

        for (int i=0; i<msgCount; i++) {
            String msg = "hello world to work mode, times = " + i;
            producer_channel.basicPublish("", workModeQueueName, null, msg.getBytes());
            System.out.println("--- work mode sent msg = " + msg);
            TimeUnit.SECONDS.sleep(1);
        }

        cdt.await();
        System.out.println("--- main thread over ---");
    }

    @Test
    public void testPubSubMode() throws IOException, InterruptedException {

        producer_channel.exchangeDeclare(pubSubExchangeName, "fanout");

        CountDownLatch cdt = new CountDownLatch(1);
        int msgCount = 15;
        int clientCount = 3;

        new Thread(() -> {
            for (int i=0; i<msgCount; i++) {
                String msg = "It is log msg " + i;
                try {
                    // client未连接上来前的消息会被直接抛弃
                    producer_channel.basicPublish(pubSubExchangeName, "", null, msg.getBytes());
                    TimeUnit.SECONDS.sleep(1);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            cdt.countDown();
        }).start();

        TimeUnit.SECONDS.sleep(1);

        for (int i=0; i<clientCount; i++) {
            String threadName = "worker-thread-" + i;
            new Thread(() -> {
                try (Connection conn = connFactory.createConnection();
                     Channel channel = conn.createChannel(false)) {
                    // .queueDeclare()默认生成一个non-durable（不持久）、exclusive（独占）、autodelete（自动删）的队列
                    // .getQueue()会产生一个随机队列名，比如：amq.gen-JzTY20BRgKO-HjmUJj0wLg
                    // 此时client如果断开了和server的连接，那么这个随机队列会自动删除
                    String queueName = channel.queueDeclare().getQueue();
                    channel.queueBind(queueName, pubSubExchangeName, "");
                    DeliverCallback deliverCallback = (consumerTag, deliver) -> {
                        String repMsg = new String(deliver.getBody(), StandardCharsets.UTF_8);
                        System.out.println("---" + threadName +" received msg = " + repMsg);
                    };

                    channel.basicConsume(queueName, true, deliverCallback, cancelCallback -> {});
                } catch (TimeoutException | IOException e) {
                    e.printStackTrace();
                }
            }).start();
            TimeUnit.SECONDS.sleep(1);
        }

        cdt.await();
        System.out.println("--- main thread is over ---");
    }

    @Test
    public void testRoutingMode() throws IOException, InterruptedException {

        producer_channel.exchangeDeclare(routingExchangeName, "direct");

        CountDownLatch cdt = new CountDownLatch(1);
        int msgCount = 10;
        int clientCount = 2;
        String[] logLevels = { "info", "warn", "error" };
        Random random = new Random();

        new Thread(() -> {
            for (int i=0; i<msgCount; i++) {
                try {
                    String logLevel = logLevels[random.nextInt(logLevels.length)];
                    // client未连接上来前的消息会被直接抛弃
                    producer_channel.basicPublish(routingExchangeName, logLevel, null, logLevel.getBytes());
                    TimeUnit.SECONDS.sleep(1);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            cdt.countDown();
        }).start();

        for (int i=0; i<clientCount; i++) {
            String threadName = "worker-thread-" + i;
            new Thread(() -> {
                try (Connection conn = connFactory.createConnection();
                     Channel channel = conn.createChannel(false)) {

                    String queueName = channel.queueDeclare().getQueue();

                    // -0监听info和warn，-1监听warn和error
                    if (threadName.endsWith("-0")) {
                        channel.queueBind(queueName, routingExchangeName, logLevels[0]);
                        channel.queueBind(queueName, routingExchangeName, logLevels[1]);
                    } else {
                        channel.queueBind(queueName, routingExchangeName, logLevels[1]);
                        channel.queueBind(queueName, routingExchangeName, logLevels[2]);
                    }

                    DeliverCallback deliverCallback = (consumerTag, deliver) -> {
                        String repMsg = new String(deliver.getBody(), StandardCharsets.UTF_8);
                        System.out.println("---" + threadName +" received msg = " + repMsg);
                    };

                    channel.basicConsume(queueName, true, deliverCallback, cancelCallback -> {});
                } catch (TimeoutException | IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        cdt.await();
        System.out.println("--- main thread is over ---");
    }

    @Test
    public void testTopicsMode() throws IOException, InterruptedException {

        producer_channel.exchangeDeclare(topicExchangeName, "topic");

        CountDownLatch cdt = new CountDownLatch(1);
        int msgCount = 10;
        int clientCount = 3;
        String[] logSources = { "kern.info", "kern.warn", "kern.error", "sys.app.error", "app.warn", "app.error" };
        Random random = new Random();

        new Thread(() -> {
            for (int i=0; i<msgCount; i++) {
                try {
                    String logSource = logSources[random.nextInt(logSources.length)];
                    // client未连接上来前的消息会被直接抛弃
                    System.out.println("--- topic mode sent msg = " + logSource);
                    producer_channel.basicPublish(topicExchangeName, logSource, null, logSource.getBytes());
                    TimeUnit.SECONDS.sleep(1);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            cdt.countDown();
        }).start();

        for (int i=0; i<clientCount; i++) {
            String threadName = "worker-thread-" + i;
            new Thread(() -> {
                try (Connection conn = connFactory.createConnection();
                     Channel channel = conn.createChannel(false)) {

                    String queueName = channel.queueDeclare().getQueue();

                    // -0监听kern的所有日志，-1监听所有error日志，-2监听长度为2个单词的error日志
                    if (threadName.endsWith("-0")) {
                        channel.queueBind(queueName, topicExchangeName, "kern.*");
                    } else if (threadName.endsWith("-1")) {
                        channel.queueBind(queueName, topicExchangeName, "#.error");
                    } else {
                        channel.queueBind(queueName, topicExchangeName, "*.error");
                    }

                    DeliverCallback deliverCallback = (consumerTag, deliver) -> {
                        String repMsg = new String(deliver.getBody(), StandardCharsets.UTF_8);
                        System.out.println("---" + threadName +" received msg = " + repMsg);
                    };

                    channel.basicConsume(queueName, true, deliverCallback, cancelCallback -> {});
                } catch (TimeoutException | IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        cdt.await();
        System.out.println("--- main thread is over ---");
    }
}
