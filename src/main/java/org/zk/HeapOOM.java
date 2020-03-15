package org.zk;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xms10m -Xmx10m -XX:+HeapDumpOnOutOfMemoryError
 * 堆溢出，可使用mat分析那个对象占用
 */
public class HeapOOM {

	static class OOMObject{

	}

	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<OOMObject>();
		int i = 0;
		while (true) {
			list.add(new OOMObject());
			if (i++ % 10000 == 0) {
				System.out.println(i);
			}
		}
	}
}
