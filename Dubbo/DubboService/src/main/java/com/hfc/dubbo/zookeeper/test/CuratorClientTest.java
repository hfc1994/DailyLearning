package com.hfc.dubbo.zookeeper.test;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by hfc on 2021/4/20.
 */
public class CuratorClientTest {

    public static void main(String[] args) throws Exception {
        String basePath = "/DailyLearning";

        CuratorFramework client = CuratorFrameworkFactory.builder()
                // 多个server之间使用逗号分隔开
                .connectString("127.0.0.1:2181")
                // 连接超时时间，默认15秒
                .connectionTimeoutMs(30 * 1000)
                // 会话超时时间，默认也是60秒
                .sessionTimeoutMs(60 * 1000)
                // 失败重试策略
                // ExponentialBackoffRetry指数回退重试
                //   - baseSleepTimeMs是初始的sleep时间，用于计算之后每次的sleep时间
                //   - maxRetries最大重试次数
                .retryPolicy(new ExponentialBackoffRetry(100, 3)).build();

        // 创建会话，阻塞至创建成功
        client.start();

        /**
         * 创建节点
         * 除非指明类型，默认是持久节点
         * zookeeper规定：所有非叶子节点都是持久节点，所以递归创建出来的节点，只有最后的数据节点才是指定的类型
         */
        // 创建一个初始内容为空的临时节点
        client.create().withMode(CreateMode.EPHEMERAL).forPath("/age");

        client.create().withMode(CreateMode.EPHEMERAL).forPath("/name", "zhangsan".getBytes());

        // 递归创建，CuratorTest是持久节点
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                .forPath(basePath + "/CuratorClientTest/address", "street".getBytes());

        /**
         * 异步创建节点
         * 如果自行指定了线程池，那么相应操作就会在该线程池中执行；
         * 如果没有，那么就会使用curator的EventThread线程对事件进行串行处理
         */
        ExecutorService executor = Executors.newFixedThreadPool(10);
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                .inBackground((curatorFramework, curatorEvent) -> {
                    System.out.println("异步创建状态 = " + curatorEvent.getResultCode());
                }, executor).forPath(basePath + "/CuratorClientTest/async-create");

        // 获取节点内容
        byte[] data = client.getData().forPath(basePath + "/CuratorClientTest/address");
        System.out.println("data = " + new String(data));

        // 获取子节点列表
        List<String> children = client.getChildren().forPath(basePath);
        System.out.println("child node :");
        children.forEach(System.out::println);
        System.out.println();

        // 使用stat，在获取内容的同时获取节点信息
        Stat stat = new Stat();
        data = client.getData().storingStatIn(stat).forPath(basePath + "/CuratorClientTest/address");
        System.out.println("data = " + new String(data));
        System.out.println(stat.getNumChildren());
        System.out.println(stat.getAversion());
        System.out.println(stat.getVersion());
        System.out.println(stat.getEphemeralOwner());
        System.out.println(stat.getCtime());

        // 创建一些用于删除的节点
        client.create().forPath("/info1");
        client.create().forPath("/info2");
        client.create().creatingParentsIfNeeded().forPath("/doc/info3");
        client.create().creatingParentsIfNeeded().forPath("/doc/info4");

        // 删除节点
        client.delete().forPath("/info1");
        // guaranteed()会在会话有效的情况下不断重试，直到删除成功
        client.delete().guaranteed().forPath("/info2");
        // 删除一个节点，并递归删除所有子节点
        client.delete().deletingChildrenIfNeeded().forPath("/doc");

        TimeUnit.SECONDS.sleep(60);
        System.out.println("--- 断开zookeeper ---");
        client.close();
        executor.shutdown();
    }

}
