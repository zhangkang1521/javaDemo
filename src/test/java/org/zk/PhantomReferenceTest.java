package org.zk;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

/**
 * -Xms20m -Xmx20m -Xmn10m
 *  虚引用必须与ReferenceQueue一起使用，当GC准备回收一个对象，如果发现它还有虚引用，就会在回收之前，把这个虚引用加入到与之关联的ReferenceQueue中
 */
public class PhantomReferenceTest {
	public static void main(String[] args) throws Exception {
		ReferenceQueue queue = new ReferenceQueue();
		List<byte[]> bytes = new ArrayList<>();

		PhantomReference<Student> reference = new PhantomReference<Student>(new Student(), queue);

		new Thread(() -> {
			for (int i = 0; i < 16;i++ ) {
				System.out.println("add:" + i);
				bytes.add(new byte[1024 * 1024]);
			}
		}).start();

		new Thread(() -> {
			while (true) {
				Reference poll = queue.poll();
				if (poll != null) {
					System.out.println("虚引用被回收了：" + poll);
				}
			}
		}).start();

		System.in.read();
	}
}
