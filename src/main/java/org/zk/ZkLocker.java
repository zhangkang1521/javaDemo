package org.zk;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte0.waiting;

/**
 * @author zhangkang
 * @date 2025/2/6 10:56
 */
@Slf4j
public class ZkLocker {

    private static final String SEQ = "seq-";

    private CuratorFramework client;

    /**
     * 当前锁创建的路径
     */
    private String currentPath;

    /**
     * 前一个临时节点
     */
    private String prevPath;

    private AtomicInteger count = new AtomicInteger(0);

    private Thread thread;

    public ZkLocker() {
        init();
    }

    public void init() {
        client = CuratorFrameworkFactory.newClient("localhost:2181", new RetryNTimes(1, 1000));
        client.start();
        log.info("初始化zk");
    }

    public void destroy() {
        log.info("关闭zk");
        client.close();
    }

    public boolean tryLock(String lockPath) {
        try {
            if (thread == null) {
                thread = Thread.currentThread();
                count.incrementAndGet();
                log.info("当前线程第一次获取锁");
            } else {
                if (thread == Thread.currentThread()) {
                    count.incrementAndGet();
                    log.info("重入锁获取成功");
                    return true;
                } else {
                    return false;
                }
            }

            currentPath = client.create()
                    .creatingParentContainersIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(lockPath + "/" + SEQ);
            List<String> children = getChildren(lockPath);
            int index = checkLocked(children);
            if (index == 0) {
                return true;
            } else if (index > 0) {
                prevPath = lockPath + "/" + children.get(index-1);
                log.info("设置prevPath:{} currentPath:{}", prevPath, currentPath);
                await();
                children = getChildren(lockPath);
                return checkLocked(children) == 0;
            }
            return false;
        } catch (Exception e) {
            log.error("获取锁异常", e);
            return false;
        }
    }

    @SneakyThrows
    private void await() {
        final CountDownLatch latch = new CountDownLatch(1);
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                log.info("监听到节点变化 watchEvent:{} prevPath:{} currentPath:{}", watchedEvent, prevPath, currentPath);
                latch.countDown();
            }
        };
        client.getData().usingWatcher(watcher).forPath(prevPath);

        log.info("线程等待 {}", currentPath);
        latch.await(3, TimeUnit.SECONDS);
        log.info("唤醒线程 {}", currentPath);
    }

    private List<String> getChildren(String lockPath) {
        try {
            List<String> children = client.getChildren().forPath(lockPath);
            Collections.sort(children);
            return children;
        } catch (Exception e) {
            log.error("获取子节点异常", e);
            return Collections.emptyList();
        }
    }

    @SneakyThrows
    private int checkLocked(List<String> children) {
        String currentSeq = currentPath.substring(currentPath.lastIndexOf("/") + 1);
        log.info("current: {} children:{}", currentSeq, children);
        return children.indexOf(currentSeq);
    }

    public void unlock() {
        try {
            Stat stat = client.checkExists().forPath(currentPath);
            log.info("currentPath:{} exist:{}", currentPath, stat);
            if (stat != null) {
                log.info("解锁 delete {}", currentPath);
                client.delete().forPath(currentPath);
            }
        } catch (Exception e) {
            log.error("释放锁异常", e);
        }
    }
}
