package org.zk;

import org.junit.Test;


public class FunnelRateLimiterTest {

	@Test
	public void test1() throws Exception{
		for (int i = 0; i < 2000; i++) {
			boolean allow = FunnelRateLimiter.isActionAllow("zk", "reply", 1000, 1);
			System.out.println(i + ": " + allow);
		}
	}

}