package org.zk;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * https://stackoverflow.com/questions/39929758/ps-marksweep-is-which-garbage-collector/44923227
 * 串行收集器
 * -XX:+UseSerialGC
 * 输出：
 * Copy 复制（新生代）
 * MarkSweepCompact 标记-整理（老年代）
 *
 * 并行收集器（使用多线程，其他与串行收集器一致）
 * -XX:+UseParallelGC  -XX:+UseParallelOldGC（不加参数jdk8默认）
 * 输出：
 * PS Scavenge（新生代）
 * PS MarkSweep （老年代）
 *
 * CMS收集器（老年代）
 * -XX:+UseConcMarkSweepGC
 * 输出：
 * ParNew （新生代）
 * ConcurrentMarkSweep （老年代）
 *
 * G1收集器，用来代替CMS
 *-XX:+UseG1GC
 * 输出：
 * G1 Young Generation
 * G1 Old Generation
 *
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
