package org.zk;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * -D可以覆盖系统属性
 * -Da.b=123 -Duser.name=byron
 */
public class SystemProp {
	public static void main(String[] args) {
		System.out.println(System.getProperty("a.b"));
		Properties properties = System.getProperties();
		Set<Map.Entry<Object, Object>> entries = properties.entrySet();
		for (Map.Entry<Object, Object> entry : entries) {
			System.out.println(entry.getKey() + " => " + entry.getValue());
		}
	}
}
