package org.zk;

/**
 * 设置栈大小：-Xss256k
 */
public class StackOverflow {

	private static int stackLength = 0;

	static void f() {
		stackLength++;
		f();
	}

	public static void main(String[] args) throws Throwable {
		try {
			f();
		} catch (Throwable e) {
			System.out.println(stackLength);
			throw e;
		}
	}
}
