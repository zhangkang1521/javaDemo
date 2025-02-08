package org.zk;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class CuratorTest {

	private CuratorFramework client;

	@Before
	public void before() {
//		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
//				.connectString("localhost:2181")
//				.retryPolicy(new RetryNTimes(1, 1000))
//				.connectionTimeoutMs(5000);
//		client = builder.build();

		client = CuratorFrameworkFactory.newClient("localhost:2181", new RetryNTimes(1, 1000));
		client.start();

//		client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
//			@Override
//			public void stateChanged(CuratorFramework client, ConnectionState state) {
//				if (state == ConnectionState.LOST) {
//					CuratorZookeeperClient.this.stateChanged(StateListener.DISCONNECTED);
//				} else if (state == ConnectionState.CONNECTED) {
//					CuratorZookeeperClient.this.stateChanged(StateListener.CONNECTED);
//				} else if (state == ConnectionState.RECONNECTED) {
//					CuratorZookeeperClient.this.stateChanged(StateListener.RECONNECTED);
//				}
//			}
//		});

	}

	@Test
	public void createPersistentNode() throws Exception {
		// 创建永久节点
		client.create()
				.creatingParentContainersIfNeeded()
				.withMode(CreateMode.PERSISTENT)
				.forPath("/parent/a", "hello".getBytes(StandardCharsets.UTF_8));
	}

	@Test
	public void createPersistentSeqNode() throws Exception {
		// 创建永久节点
		client.create()
				.creatingParentContainersIfNeeded()
				.withMode(CreateMode.PERSISTENT_SEQUENTIAL)
				.forPath("/parent/b", "hello".getBytes(StandardCharsets.UTF_8));
	}

	@Test
	public void createEphemeralNode() throws Exception {
		// 创建临时节点
		for (int i = 0; i < 10; i++) {
			// 每次都是递增，跟目录无关
			String path1 = client.create()
					.withMode(CreateMode.PERSISTENT_SEQUENTIAL)
					.forPath("/eee-");
			String path2 = client.create()
					.withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
					.forPath("/fff-");
			System.out.println(path1 + " " + path2);
		}
	}

	@Test
	public void checkExists() throws Exception {
		Stat stat = client.checkExists().forPath("/aa");
		System.out.println(stat);
	}

	@Test
	public void getData() throws Exception {
		byte[] data = client.getData().forPath("/parent/a");
		System.out.println(new String(data));

		List<String> children = client.getChildren().forPath("/parent");
		System.out.println(children);
	}

	@Test
	public void updateData() throws Exception {
		client.setData().forPath("/aa", "test2".getBytes(StandardCharsets.UTF_8));
	}

	@Test
	public void updateDataBackground() throws Exception {

	}


	public static void main(String[] args) throws Exception {
		CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", new RetryNTimes(1, 1000));
		client.start();
		client.setData()
				.inBackground(new AsyncCallback.StringCallback() {

					@Override
					public void processResult(int i, String s, Object o, String s1) {
						System.out.println("i");
					}
				})
				.forPath("/aa", "test5".getBytes(StandardCharsets.UTF_8));

		Thread.sleep(10000);
		client.close();
	}



	@After
	public void after() {
		client.close();
	}
}
