package org.zk;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * -XX:+PrintGCDetails
 */
public class Gc {

	private byte[] data = new byte[2*1024*1024];

	public static void main(String[] args) {
		List<GarbageCollectorMXBean> beans = ManagementFactory.getGarbageCollectorMXBeans();
		for (GarbageCollectorMXBean bean : beans)
		{
			System.out.println(bean.getName());
		}

		Gc gc = new Gc();
		gc = null;
		System.gc();
	}
}
