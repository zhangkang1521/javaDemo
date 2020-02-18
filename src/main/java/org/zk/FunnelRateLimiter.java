package org.zk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 漏斗限流
 */
public class FunnelRateLimiter {
	static class Funnel {
		private Logger logger = LoggerFactory.getLogger(Funnel.class);
		int capacity; // 容量
		int leakRate; // 漏水速率 单位/s
		int leftQuota; // 剩余容量
		long lastLeakTs; // 最后一次漏水时间

		public Funnel(int capacity, int leakRate) {
			this.capacity = capacity;
			this.leakRate = leakRate;
			this.leftQuota = capacity;
			this.lastLeakTs = System.currentTimeMillis();
		}

		// 漏水
		public void makeSpace() {
			logger.info("漏水前剩余容量：{}", leftQuota);
			long nowTs = System.currentTimeMillis();
			int delta = (int)(nowTs - lastLeakTs) * leakRate;
			if (leftQuota + delta > capacity) {
				this.leftQuota = capacity;
			} else {
				this.leftQuota += delta;
			}
			lastLeakTs = nowTs;
			logger.info("漏水后剩余容量：{}", leftQuota);
		}

		// 加水
		public boolean watering(int quota) {

			makeSpace();
			logger.info("加水前剩余容量：{}" , leftQuota);
			if (leftQuota - quota < 0) {
				return false;
			} else {
				leftQuota -= quota;
				logger.info("加水后剩余容量：{}" , leftQuota);
				return true;
			}

		}
	}

	private static Map<String, Funnel> map = new HashMap<>();

	public static boolean isActionAllow(String userId, String action, int capacity, int leakRate) {
		String key = userId + action;
		Funnel funnel = map.get(key);
		if (funnel == null) {
			funnel = new Funnel(capacity, leakRate);
			map.put(key, funnel);
		}
		return funnel.watering(1);
	}

}
