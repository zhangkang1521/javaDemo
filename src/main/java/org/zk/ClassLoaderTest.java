package org.zk;

import java.io.IOException;
import java.io.InputStream;

public class ClassLoaderTest {

	public static void main(String[] args) throws Exception {
		ClassLoader myLoader = new ClassLoader() {
			@Override
			public Class<?> loadClass(String name) throws ClassNotFoundException {
				try {
					InputStream in = getClass().getResourceAsStream(name.substring(name.lastIndexOf(".") + 1) + ".class");
					if (in == null) {
						return super.loadClass(name);
					}
					byte[] bytes = new byte[in.available()];
					in.read(bytes);
					return defineClass(name, bytes, 0, bytes.length);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return super.findClass(name);
			}
		};
		Class cls = myLoader.loadClass("org.zk.ClassLoaderTest");
		System.out.println(cls == ClassLoaderTest.class);
		System.out.println(cls.newInstance() instanceof ClassLoaderTest);
		System.out.println(new ClassLoaderTest() instanceof ClassLoaderTest);
	}
}
