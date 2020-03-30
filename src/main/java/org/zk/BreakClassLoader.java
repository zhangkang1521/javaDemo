package org.zk;

import com.sun.org.apache.bcel.internal.generic.LoadClass;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
//import java.nio.file.Files;
//import java.nio.file.Paths;


public class BreakClassLoader extends ClassLoader {

	public BreakClassLoader() {

	}

	public BreakClassLoader(ClassLoader parent) {
		super(parent);
	}

	// 打破双亲委派
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		Class<?> clazz = null;
		ClassLoader system = getSystemClassLoader();
		try {
			clazz = system.loadClass(name);
		} catch (Exception e) {
			// ignore
		}
		if (clazz != null)
			return clazz;
		clazz = findClass(name);
		return clazz;
	}

	protected Class<?> findClass(String name) throws ClassNotFoundException {
//		try {
//			byte[] bytes = Files.readAllBytes(Paths.get("E:/Object.class"));
//			Class<?> c = this.defineClass(name, bytes, 0, bytes.length);
//			return c;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return super.findClass(name);
	}

}
