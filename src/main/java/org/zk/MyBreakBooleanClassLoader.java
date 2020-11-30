package org.zk;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class MyBreakBooleanClassLoader extends ClassLoader {

	public MyBreakBooleanClassLoader() {
		// 默认Parent为sun.misc.Launcher$AppClassLoader
	}

	public MyBreakBooleanClassLoader(ClassLoader parent) {
		super(parent);
	}


	// 打破双亲委派，重写loadClass
	public Class<?> loadClass(String name) throws ClassNotFoundException  {
		if ("java.lang.Boolean".equals(name)) {
			File file = getClassFile(name);
			try {
				byte[] bytes = getClassBytes(file);
				Class<?> c = this.defineClass(name, bytes, 0, bytes.length);
				return c;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		} else { // 其他类使用系统原有逻辑
			return super.loadClass(name);
		}
	}


	private File getClassFile(String name) {
		File file = new File("E:/java/lang/Boolean.class");
		return file;
	}

	private byte[] getClassBytes(File file) throws Exception {
		// 这里要读入.class的字节，因此要使用字节流
		FileInputStream fis = new FileInputStream(file);
		FileChannel fc = fis.getChannel();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		WritableByteChannel wbc = Channels.newChannel(baos);
		ByteBuffer by = ByteBuffer.allocate(1024);

		while (true) {
			int i = fc.read(by);
			if (i == 0 || i == -1)
				break;
			by.flip();
			wbc.write(by);
			by.clear();
		}

		fis.close();

		return baos.toByteArray();
	}
}
