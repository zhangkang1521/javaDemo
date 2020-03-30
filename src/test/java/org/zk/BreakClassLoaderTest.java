package org.zk;

import org.junit.Test;

import static org.junit.Assert.*;

public class BreakClassLoaderTest {

	@Test
	public void test1() throws Exception {
				Class cls = Class.forName("java.lang.Object", true, new BreakClassLoader());
//		Class cls = Class.forName("Person", true, new BreakClassLoader());
		Object obj = cls.newInstance();
		System.out.println(obj.getClass());
		System.out.println(cls.getClassLoader());
	}

}