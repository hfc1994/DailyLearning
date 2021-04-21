package com.hfc.dubbo.zookeeper.test;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.TimeUnit;

/**
 * Created by hfc on 2021/4/20.
 */
public class CuratorDetectTest {

    public static void main(String[] args) throws Exception {
        String basePath = "/DailyLearning";

        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .connectionTimeoutMs(30 * 1000)
                .sessionTimeoutMs(60 * 1000)
                .retryPolicy(new ExponentialBackoffRetry(100, 3)).build();
        client.start();

        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                .forPath(basePath + "/config/app.name", "".getBytes());

        /**
         * 监听指定节点本身的变化，包括节点本身的创建和节点本身数据的变化
         */
        NodeCache nodeCache = new NodeCache(client, basePath + "/config/app.name");
        nodeCache.getListenable().addListener(() -> {
            System.out.println("节点的新数据是 = " + new String(nodeCache.getCurrentData().getData()));
        });
        nodeCache.start(true);

        // 手动更新数据
        client.setData().forPath(basePath + "/config/app.name", "CuratorDetectTest".getBytes());
        // 不加休眠会导致两次更新时间相近，客户端接收到的是合并后的监听信息
        TimeUnit.SECONDS.sleep(1);
        // 监听会一直进行，而不是zookeeper默认的一次性监听
        client.setData().forPath(basePath + "/config/app.name", "cccccc".getBytes());

        /**
         * 监听子节点变化情况
         * 1、新增子节点
         * 2、删除子节点
         * 3、子节点数据变更
         */
        PathChildrenCache pcCache = new PathChildrenCache(client, basePath + "/config", true);
        pcCache.getListenable().addListener((zkClient, event) -> {
            switch (event.getType()) {
                case CHILD_ADDED:
                    System.out.println("- 新增子节点 -");
                    break;
                case CHILD_UPDATED:
                    System.out.println("- 子节点数据变化 -");
                    break;
                case CHILD_REMOVED:
                    System.out.println("- 删除子节点 -");
                    break;
                default:
                    break;
            }
            System.out.println(event.getData().getPath() + " = " + new String(event.getData().getData()));
        });
        pcCache.start();

        // 手动触发生成事件
        // 新增子节点
        client.create().withMode(CreateMode.EPHEMERAL)
                .forPath(basePath + "/config/app.version", "v1".getBytes());
        client.create().withMode(CreateMode.EPHEMERAL)
                .forPath(basePath + "/config/app.author", "hfc".getBytes());
        TimeUnit.SECONDS.sleep(1);
        // 更新子节点
        client.setData().forPath(basePath + "/config/app.version", "v2".getBytes());
        client.setData().forPath(basePath + "/config/app.version", "v3".getBytes());
        client.setData().forPath(basePath + "/config/app.version", "v4".getBytes());
        client.setData().forPath(basePath + "/config/app.version", "v5".getBytes());
        client.setData().forPath(basePath + "/config/app.version", "v6".getBytes());
        client.setData().forPath(basePath + "/config/app.author", "fc".getBytes());
        client.setData().forPath(basePath + "/config/app.author", "fcc".getBytes());
        TimeUnit.SECONDS.sleep(1);
        // 删除子节点
        client.delete().forPath(basePath + "/config/app.version");

        System.out.println("- 获取目录下全部数据 -");
        pcCache.getCurrentData().forEach(childData -> {
            System.out.println(childData.getPath() + " = " + new String(childData.getData()));
        });

        TimeUnit.SECONDS.sleep(30);
        pcCache.close();
        nodeCache.close();
    }

}
