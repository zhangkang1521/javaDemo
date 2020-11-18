package org.zk;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * -Xms20m -Xmx20m -Xmn10m 
 */
public class WeakReferenceTest {

	public static void main(String[] args) {
		WeakReference<byte[]> softReference = new WeakReference<byte[]>(new byte[1024*1024*9]);
		System.out.println(softReference.get());
		System.gc(); // 弱引用，只要gc就被回收
		System.out.println(softReference.get());

	}
}
