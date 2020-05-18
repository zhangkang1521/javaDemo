package org.zk;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.junit.Before;
import org.junit.Test;

public class CuratorTest {

	private CuratorFramework client;

	@Before
	public void before() {
		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
				.connectString("localhost:2181")
				.retryPolicy(new RetryNTimes(1, 1000))
				.connectionTimeoutMs(5000);
		client = builder.build();
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
		client.start();
	}

	@Test
	public void testCreate() throws Exception {
		client.create().withMode(CreateMode.EPHEMERAL).forPath("/a");
		client.close();
	}
}
