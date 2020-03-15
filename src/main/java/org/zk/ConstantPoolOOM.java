package org.zk;

import java.util.ArrayList;
import java.util.List;

/**
 * jdk6: -XX:PermSize=11M -XX:MaxPermSize=10M 常量池存在方法区
 * jdk7中将常量池从方法区移到堆区
 */
public class ConstantPoolOOM {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		int i = 0;
		while (true) {
			list.add(String.valueOf(i++).intern());
		}
	}
}
