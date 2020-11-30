package org.zk;

import org.junit.Test;
import sun.util.cldr.CLDRLocaleDataMetaInfo;

//import sun.util.cldr.CLDRLocaleDataMetaInfo;

public class MyClassLoaderTest {



	@Test
	public void test3ClassLoader() {
		// bootstrap
		System.out.println("bootstrapClassLoader dir:" + System.getProperty("sun.boot.class.path"));
		System.out.println(Boolean.class.getClassLoader());
		// ext
		System.out.println("ExtClassLoader dir:" + System.getProperty("java.ext.dirs"));
		System.out.println(CLDRLocaleDataMetaInfo.class.getClassLoader());
		// app
		System.out.println("AppClassLoader dir:" + System.getProperty("java.class.path"));
		System.out.println(MyClassLoaderTest.class.getClassLoader());
		System.out.println("AppClassLoader's parent " + MyClassLoaderTest.class.getClassLoader().getParent());
	}

	@Test
	public void testBreak() throws Exception  {
		// classpath中也存在，由于双亲委派所以会使用AppClassLoader
//		Class cls = Class.forName("org.zk.Person", true, new MyClassLoader());
		// 打破双亲委派，会报错：java.lang.ClassCastException: org.zk.Person cannot be cast to org.zk.Person
		Class cls = Class.forName("org.zk.Person", true, new MyBreakClassLoader());
		Person obj = (Person)cls.newInstance();
		obj.sayHello();
		System.out.println(cls.getClassLoader());
	}

	@Test
	public void testBreakBoolean() throws Exception {
		// 无法打破 java.lang.SecurityException: Prohibited package name: java.lang
		Class cls = Class.forName("java.lang.Boolean", true, new MyBreakBooleanClassLoader());
		System.out.println(cls.getClassLoader());
//
	}





}