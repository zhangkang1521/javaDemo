package org.zk;

import org.junit.Test;
import sun.util.cldr.CLDRLocaleDataMetaInfo;
//import sun.util.cldr.CLDRLocaleDataMetaInfo;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class MyClassLoaderTest {

	@Test
	public void test1() throws Exception {
		// 由于双亲委派模型所以java.lang.Object不会被MyClassLoader加载
//		Class cls = Class.forName("java.lang.Object", true, new MyClassLoader());
		Class cls = Class.forName("Person", true, new MyClassLoader());
		Object obj = cls.newInstance();
		System.out.println(obj.getClass());
		System.out.println(cls.getClassLoader());
	}

	@Test
	public void test2() {
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



}