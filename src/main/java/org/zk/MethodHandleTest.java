package org.zk;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleTest {

	static class A {
		public void println(String str) {
			System.out.println("hi " + str);
		}
	}

	public static void main(String[] args) throws Throwable {
		getPrintln(System.out).invokeExact("hello");
		getPrintln(new A()).invokeExact("hello");
	}

	private static MethodHandle getPrintln(Object receiver) throws Exception {
		MethodType mt = MethodType.methodType(void.class, String.class);
		return MethodHandles.lookup().findVirtual(receiver.getClass(), "println", mt).bindTo(receiver);
	}
}
