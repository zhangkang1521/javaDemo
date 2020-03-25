package org.zk;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProfileMemoryTest {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();
		f1();
	}

	private static void f1() {
		new A();
		f2();
	}

	private static void f2() {
		new B();
	}

	static class A {
		byte[] data = new byte[1024 * 1024];
	}

	static class B {
		byte[] b_data = new byte[1024 * 1024/4];
	}
}


