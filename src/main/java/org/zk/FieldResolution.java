package org.zk;

public class FieldResolution {

	interface Interface0 {
		int a = 0;
	}

	interface Interface1 {
		int a = 0;
	}


	static class Parent {
//		static int a = 1;
	}

	static class Sub extends Parent implements Interface0 {
//		static int a = 2;
	}

	public static void main(String[] args) {
		System.out.println(Sub.a);
	}
}
