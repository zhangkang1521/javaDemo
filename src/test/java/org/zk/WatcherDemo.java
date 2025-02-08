package org.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author zhangkang
 * @date 2025/2/2 17:09
 */
@Slf4j
public class WatcherDemo {

    private CuratorFramework client;

    @Before
    public void before() {
        client = CuratorFrameworkFactory.newClient("localhost:2181", new RetryNTimes(1, 1000));
        client.start();
    }

    @After
    public void after() {
        client.close();
    }

    @Test
    public void watcher() throws Exception {
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                // 只会监听到一次变化
                System.out.println(watchedEvent);
            }
        };
        client.getData().usingWatcher(watcher).forPath("/test2");

//        client.setData().forPath("/test", "111".getBytes(StandardCharsets.UTF_8));
//        client.setData().forPath("/test", "222".getBytes(StandardCharsets.UTF_8));
//        client.delete().forPath("/test2");

        System.in.read();
    }

    @Test
    public void nodeCache() throws Exception {
        NodeCache nodeCache = new NodeCache(client, "/test2");
        NodeCacheListener nodeCacheListener = new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                ChildData childData = nodeCache.getCurrentData();
                System.out.println("节点变化了"+new String(childData.getData()));
            }
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();

        client.setData().forPath("/test2", "5".getBytes(StandardCharsets.UTF_8));
//        Thread.sleep(1000);
        client.setData().forPath("/test2", "6".getBytes(StandardCharsets.UTF_8));

        System.in.read();
    }

    @Test
    public void pathChildrenCache() throws Exception {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "/test2", true);
        PathChildrenCacheListener listener = new PathChildrenCacheListener() {

            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                log.info("子节点变动 type:{}", event.getType());
            }
        };
        pathChildrenCache.getListenable().addListener(listener);
        pathChildrenCache.start();

        client.create().forPath("/test2/a", "aa".getBytes(StandardCharsets.UTF_8));
        client.create().forPath("/test2/b", "bb".getBytes(StandardCharsets.UTF_8));
        client.setData().forPath("/test2/b", "bb2".getBytes(StandardCharsets.UTF_8));
        client.delete().forPath("/test2/a");
        client.delete().forPath("/test2/b");

        System.in.read();
    }

    @Test
    public void treeCache() throws Exception {
        TreeCache treeCache = new TreeCache(client, "/test2");
        TreeCacheListener listener = new TreeCacheListener() {

            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                log.info("节点变动 type:{}", event.getType());
                if(event.getData() != null) {
                    log.info("节点:{}", event.getData().getPath());
                }
            }
        };
        treeCache.getListenable().addListener(listener);
        treeCache.start();

        client.create().forPath("/test2/a", "aa".getBytes(StandardCharsets.UTF_8));
        client.create().forPath("/test2/b", "bb".getBytes(StandardCharsets.UTF_8));
        client.setData().forPath("/test2/b", "bb2".getBytes(StandardCharsets.UTF_8));
        client.setData().forPath("/test2", "222".getBytes(StandardCharsets.UTF_8));
        client.delete().forPath("/test2/a");
        client.delete().forPath("/test2/b");

        System.in.read();
    }
}
